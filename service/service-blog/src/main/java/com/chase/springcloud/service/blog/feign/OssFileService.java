package com.chase.springcloud.service.blog.feign;


import com.chase.springcloud.common.base.model.R;
import com.chase.springcloud.service.blog.feign.fallback.OssFileServiceFallBack;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author hzb
 * @since 2020/4/17
 */
@Service
@FeignClient(value = "service-oss", fallback = OssFileServiceFallBack.class)
public interface OssFileService {

    @GetMapping("/admin/oss/file/test")
    R test();

    @DeleteMapping("/admin/oss/file/remove")
    R removeFile(@RequestBody String url);

    @DeleteMapping("remove-batch")
    public R removeFileBatch(@RequestBody List<String> urlList);
}
