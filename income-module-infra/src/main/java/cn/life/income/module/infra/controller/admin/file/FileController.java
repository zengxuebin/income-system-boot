package cn.life.income.module.infra.controller.admin.file;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.life.income.framework.common.pojo.CommonResult;
import cn.life.income.framework.common.pojo.PageResult;
import cn.life.income.framework.common.util.object.BeanUtils;
import cn.life.income.module.infra.controller.admin.file.vo.file.*;
import cn.life.income.module.infra.dal.dataobject.file.FileDO;
import cn.life.income.module.infra.service.file.FileService;
import jakarta.annotation.Resource;
import jakarta.annotation.security.PermitAll;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static cn.life.income.framework.common.pojo.CommonResult.success;
import static cn.life.income.module.infra.framework.file.core.utils.FileTypeUtils.writeAttachment;

/**
 * 管理后台 - 文件存储 Controller
 */
@RestController
@RequestMapping("/infra/file")
@Validated
@Slf4j
public class FileController {

    @Resource
    private FileService fileService;

    /**
     * 上传文件
     *
     * 模式一：后端上传文件
     *
     * @param uploadReqVO 上传文件的请求对象
     * @return 上传结果
     * @throws Exception 上传过程中的异常
     */
    @PostMapping("/upload")
    public CommonResult<String> uploadFile(@Valid FileUploadReqVO uploadReqVO) throws Exception {
        MultipartFile file = uploadReqVO.getFile();
        byte[] content = IoUtil.readBytes(file.getInputStream());
        return success(fileService.createFile(content, file.getOriginalFilename(),
                uploadReqVO.getDirectory(), file.getContentType()));
    }

    /**
     * 获取文件预签名地址（上传）
     *
     * 模式二：前端上传文件：用于前端直接上传七牛、阿里云 OSS 等文件存储器
     *
     * @param name 文件名称
     * @param directory 文件目录
     * @return 文件预签名 URL
     */
    @GetMapping("/presigned-url")
    public CommonResult<FilePreSignedUrlRespVO> getFilePresignedUrl(
            @RequestParam("name") String name,
            @RequestParam(value = "directory", required = false) String directory) {
        return success(fileService.presignPutUrl(name, directory));
    }

    /**
     * 创建文件记录
     *
     * 模式二：前端上传文件：配合 presigned-url 接口，记录上传的文件
     *
     * @param createReqVO 文件创建请求对象
     * @return 创建结果，返回文件 ID
     */
    @PostMapping("/create")
    public CommonResult<Long> createFile(@Valid @RequestBody FileCreateReqVO createReqVO) {
        return success(fileService.createFile(createReqVO));
    }

    /**
     * 获取文件信息
     *
     * @param id 文件的唯一编号
     * @return 文件信息响应对象
     */
    @GetMapping("/get")
    @PreAuthorize("@ss.hasPermission('infra:file:query')")
    public CommonResult<FileRespVO> getFile(@RequestParam("id") Long id) {
        return success(BeanUtils.toBean(fileService.getFile(id), FileRespVO.class));
    }

    /**
     * 删除文件
     *
     * @param id 文件的唯一编号
     * @return 删除结果
     * @throws Exception 删除过程中的异常
     */
    @DeleteMapping("/delete")
    @PreAuthorize("@ss.hasPermission('infra:file:delete')")
    public CommonResult<Boolean> deleteFile(@RequestParam("id") Long id) throws Exception {
        fileService.deleteFile(id);
        return success(true);
    }

    /**
     * 批量删除文件
     *
     * @param ids 文件的唯一编号列表
     * @return 批量删除结果
     * @throws Exception 删除过程中的异常
     */
    @DeleteMapping("/delete-list")
    @PreAuthorize("@ss.hasPermission('infra:file:delete')")
    public CommonResult<Boolean> deleteFileList(@RequestParam("ids") List<Long> ids) throws Exception {
        fileService.deleteFileList(ids);
        return success(true);
    }

    /**
     * 下载文件
     *
     * @param request HTTP 请求对象
     * @param response HTTP 响应对象
     * @param configId 配置编号
     * @throws Exception 下载过程中的异常
     */
    @GetMapping("/{configId}/get/**")
    @PermitAll
    public void getFileContent(HttpServletRequest request,
                               HttpServletResponse response,
                               @PathVariable("configId") Long configId) throws Exception {
        // 获取请求的路径
        String path = StrUtil.subAfter(request.getRequestURI(), "/get/", false);
        if (StrUtil.isEmpty(path)) {
            throw new IllegalArgumentException("结尾的 path 路径必须传递");
        }
        // 解码，解决中文路径的问题
        path = URLUtil.decode(path, StandardCharsets.UTF_8, false);

        // 读取内容
        byte[] content = fileService.getFileContent(configId, path);
        if (content == null) {
            log.warn("[getFileContent][configId({}) path({}) 文件不存在]", configId, path);
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return;
        }
        writeAttachment(response, path, content);
    }

    /**
     * 获取文件分页
     *
     * @param pageVO 文件分页请求对象
     * @return 文件分页结果
     */
    @GetMapping("/page")
    @PreAuthorize("@ss.hasPermission('infra:file:query')")
    public CommonResult<PageResult<FileRespVO>> getFilePage(@Valid FilePageReqVO pageVO) {
        PageResult<FileDO> pageResult = fileService.getFilePage(pageVO);
        return success(BeanUtils.toBean(pageResult, FileRespVO.class));
    }
}
