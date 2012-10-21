package kr.co.blog.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * 
 * @author kbtapjm
 *
 */
public class FileUtil extends HttpServletRequestWrapper {
    
    public FileUtil(HttpServletRequest request) {
        super(request);
    }
    
    public final static String PATH = "C:\\web_dev\\repasitory\\";

    /**
     * 파일 업로드
     * @param file
     * @return
     */
    public static boolean fileUpload(MultipartFile file) {
        String fileName = "";
        boolean uploadResult = false;
        
        try {
            fileName = file.getOriginalFilename();
            
            File uploadedFile = new File(PATH, fileName);
            
            if (uploadedFile.exists()) {
                for(int k = 0;  true; k++) {
                    uploadedFile = new File(PATH, "(" + k + ")" + fileName);
                    
                    if(!uploadedFile.exists()) { 
                        fileName = "("+k+")"+fileName;
                        break;
                    }
                }
            }
            
            file.transferTo(new File(PATH + fileName));
            uploadResult = true;
        } catch (Exception e) {
            System.out.println("File Upload Fail :" + fileName);
            System.out.println("Exception :"+e.toString());
        } 
        
        return uploadResult;
    }

    /**
     * 파일 삭제
     * @param fileName
     * @return
     */
    public static boolean fileDelete(String fileName) {
        boolean ret = false;
        
        try {
            if(fileName != null) { 
                File file = new File(PATH + fileName);
                ret = file.delete();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        return ret;
    }

    /**
     * 파일 다운로드
     * @param response
     * @param file
     * @throws IOException
     */
    public static void fileDownload(HttpServletResponse response, File file) throws IOException {  
        response.setContentLength((int) file.length());
        response.setHeader("Content-Disposition", "attachment; fileName=\"" + java.net.URLEncoder.encode(file.getName(), "UTF-8") + "\";");
        response.setHeader("Content-Transfer-Encoding", "binary");
        OutputStream out = response.getOutputStream();
        FileInputStream fis = null;
        
        try {
            fis = new FileInputStream(file);
            FileCopyUtils.copy(fis, out);
        } catch(Exception e) {
            System.out.println("file not found");
            e.printStackTrace();
        } finally {
            if (fis != null)
                try {
                    fis.close();
                } catch (IOException ex) {
            }
        }
        out.flush();
    }

    /**
     * 파일 타입 얻기
     * @param filename
     * @return
     */
    public static String getFileType(String filename) {
        if(filename == null || filename.length() == 0 ) return ""; 
        
        int pathPoint = filename.lastIndexOf(".");
        String type = filename.substring(pathPoint + 1, filename.length());
        
        return type.toLowerCase();
    }

    /**
     * 파일 사이즈 계산
     * @param cbyte
     * @return
     */
    public static String getFileSize(long cbyte) {
        String result = "";
        long _kb = 1024;
        long _mb = _kb * 1024;
        long _gb = _mb * 1024;
        float retSize = 0;

        if (cbyte < _kb) {
            result = cbyte + " Byte ";
        } else if (cbyte < _mb) {
            retSize = (float)cbyte / (float)_kb;
            retSize = (float)((int)(retSize * 100)) / 100;
            result = retSize + " KB ";
        } else if (cbyte < _gb) {
            retSize = (float)cbyte / (float)_mb;
            retSize = (float)((int)(retSize * 100)) / 100;
            result = retSize + " MB ";
        } else {
            retSize = (float)cbyte / (float)_gb;
            retSize = (float)((int)(retSize * 100)) / 100;
            result = retSize + " GB ";
        }
        
        return result;
    }
}
