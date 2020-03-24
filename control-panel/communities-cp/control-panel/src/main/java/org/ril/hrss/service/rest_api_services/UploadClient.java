package org.ril.hrss.service.rest_api_services;

import org.ril.hrss.utility.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Service
public class UploadClient {

    @Autowired
    RestTemplate restTemplate;
    @Value("${spring.upload.url}")
    private String serverUrl;
    @Autowired
    private StorageConfigService storageConfigService;

    private static File convert(MultipartFile file) {
        File convFile = new File(file.getOriginalFilename());
        try {
            convFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(file.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return convFile;
    }

    public ResponseEntity UploadClient(@RequestParam("map") MultiValueMap map, @RequestParam("file") MultipartFile file) {
        MultiValueMap<String, Object> bodyMap = new LinkedMultiValueMap<>();
        bodyMap.addAll(map);
        bodyMap.add(Constants.FILE, new FileSystemResource(convert(file)));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(bodyMap, headers);
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        ResponseEntity<Map> response = null;
        try {
            response = restTemplate.exchange(serverUrl, HttpMethod.POST, requestEntity, Map.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    public String getRelativePath(String absolutePath, HttpServletRequest httpServletRequest, Integer is_org_level, Integer orgId) {
        Map map = storageConfigService.getStorageConfig(is_org_level, orgId);
        Map azure = new HashMap();
        if (map.containsKey(Constants.AZURE)) {
            azure = (Map) map.get(Constants.AZURE);
        }
        String folderName = null;
        String downloadBasePath = null;
        if (map != null) {
            folderName = map.get(Constants.UPLOAD_FOLDER_NAME).toString();
            downloadBasePath = azure.get(Constants.DOWNLOAD_BASE_PATH).toString();
        }
        URI basePath = URI.create(downloadBasePath + Constants.SEPARATOR + folderName);
        URI absolute = URI.create(absolutePath);
        URI relative = basePath.relativize(absolute);
        return relative.toString();
    }
}
