package kr.co.blog.common.extract;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.AbstractView;

public class CsvUtil extends AbstractView implements OasisPotalViewer {
    private Queue<String> headQueue = new LinkedList<String>();
    private String fileName = "oasis";
    private StringBuilder sb = new StringBuilder();
    
    @Override
    public void init(Object object) {
        headQueue.add("a");
        headQueue.add("b");
        headQueue.add("c");
        headQueue.add("d");
        
        while ( headQueue.peek() != null){
        
            sb.append( "\"" + headQueue.poll() + "\"" );
            
            if ( headQueue.size() > 0 ){
                sb.append(",");
            }
        }
        
    }

    @Override
    public void setSheetName(String sheetName) {
        
    }
    
    @Override
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    @Override
    public ModelAndView download(Object object) {
        
        try {
            init(object);
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        return new ModelAndView(this);
    }
    
    @Override
    protected void renderMergedOutputModel(
            Map<String, Object> model, 
            HttpServletRequest request, 
            HttpServletResponse response) throws Exception {
        
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        
        response.setHeader("Content-Type", "text/csv; charset=MS949");
        response.setHeader("Content-Disposition", "attachment;filename=" + java.net.URLEncoder.encode(this.fileName + "_"+ sdf.format(c.getTime()) + ".csv","UTF-8").replace('+',' ').replaceAll(" ","%20"));
   
        PrintWriter out = response.getWriter();
        out.print( sb.toString() );
    }
}
