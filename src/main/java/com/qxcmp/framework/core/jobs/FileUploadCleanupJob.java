package com.qxcmp.framework.core.jobs;

import com.qxcmp.framework.config.SystemConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;

import static com.qxcmp.framework.core.QXCMPConfiguration.QXCMP_FILE_UPLOAD_TEMP_FOLDER;
import static com.qxcmp.framework.core.QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_FILE_UPLOAD_TEMP_FILE_RESERVE_DURATION;
import static com.qxcmp.framework.core.QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_FILE_UPLOAD_TEMP_FILE_RESERVE_DURATION_DEFAULT_VALE;

/**
 * 上传临时文件清理定是任务
 *
 * @author Aaric
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class FileUploadCleanupJob {

    /**
     * 清理任务每天执行一次
     */
    private final long INTERVAL = 3600000 * 24;

    private final SystemConfigService systemConfigService;

    @Scheduled(initialDelay = INTERVAL, fixedRate = INTERVAL)
    public void cleanUp() {
        log.info("Clean up upload file tmp folder...");
        long currentTimeMillis = System.currentTimeMillis();
        int duration = systemConfigService.getInteger(SYSTEM_CONFIG_FILE_UPLOAD_TEMP_FILE_RESERVE_DURATION).orElse(SYSTEM_CONFIG_FILE_UPLOAD_TEMP_FILE_RESERVE_DURATION_DEFAULT_VALE) * 60 * 1000;

        FileUtils.listFilesAndDirs(new File(QXCMP_FILE_UPLOAD_TEMP_FOLDER), TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE).stream().filter(File::isDirectory).forEach(file -> {
            try {
                String timestamp = StringUtils.substringAfterLast(file.getPath(), File.separator);

                if (currentTimeMillis - Long.parseLong(timestamp) > duration) {
                    FileUtils.forceDelete(file);
                    log.info("Remove tmp upload file {}", file.getAbsolutePath());
                }

            } catch (Exception ignored) {

            }
        });
    }
}
