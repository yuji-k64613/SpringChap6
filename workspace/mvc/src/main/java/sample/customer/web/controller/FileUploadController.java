package sample.customer.web.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileUploadController {
	private static final Log LOG = LogFactory.getLog(FileUploadController.class);

    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public String showUploadForm() {
        return "upload";
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String uploadFile(
        @RequestParam("uploadFile") MultipartFile multipartFile) 
                                            throws IOException{
        // ファイル名
        String fileName = multipartFile.getOriginalFilename();
        // ファイルの大きさ（単位はbyte）
        long size = multipartFile.getSize();
        // コンテンツタイプ
        String contentType = multipartFile.getContentType();
        // 内容（byte配列）
        byte[] fileContents = multipartFile.getBytes();

        // ファイルとして保存
        multipartFile.transferTo(new File("/tmp/" + fileName));

        InputStream is = null;
        try {
            // ファイルの内容を読み込むためのInputStream
            is = multipartFile.getInputStream();

            // InputStreamを使用した処理
        } finally {
        	// 必ずcloseする
            is.close();
        }

        LOG.trace("size=" + size);
        LOG.trace("contentType=" + contentType);
        LOG.trace("fileContents=" + new String(fileContents, "UTF-8"));

        return "redirect:/upload";
    }
}
