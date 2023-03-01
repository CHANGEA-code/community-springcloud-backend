package com.chase.springcloud.service.blog.feign.fallback;

import com.chase.springcloud.common.base.model.R;
import com.chase.springcloud.service.blog.feign.OssFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hzb
 * @since 2020/4/17
 */
@Service
@Slf4j
public class OssFileServiceFallBack implements OssFileService {

    @Override
    public R test() {
        return R.error();
    }

    @Override
    public R removeFile(String url) {
        log.info("removeFile方法熔断保护");
        return R.error();
    }

    @Override
    public R removeFileBatch(List<String> urlList) {
        log.info("removeFileBatch方法熔断保护");
        return R.error();
    }
}
