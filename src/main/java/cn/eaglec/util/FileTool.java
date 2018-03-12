
package cn.eaglec.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

public class FileTool
{
    private static Logger log = Logger.getLogger(FileTool.class);
    private static volatile FileTool instance;

    private FileTool()
    {
    }

    public static FileTool getinstance()
    {
        if (instance == null)
        {
            synchronized (FileTool.class)
            {
                if (instance == null)
                {
                    instance = new FileTool();
                }
            }
        }
        return instance;
    }
    
    
    /**
	 * 判断文件是否存在
	 * 
	 * @param filePath
	 * @return
	 */
	public static boolean creatFile(String destFileName) {
		File file = new File(destFileName);  
        if(file.exists()) {  
            System.out.println("创建单个文件" + destFileName + "失败，目标文件已存在！");  
            return false;  
        }  
        if (destFileName.endsWith(File.separator)) {  
            System.out.println("创建单个文件" + destFileName + "失败，目标文件不能为目录！");  
            return false;  
        }  
        //判断目标文件所在的目录是否存在  
        if(!file.getParentFile().exists()) {  
            //如果目标文件所在的目录不存在，则创建父目录  
            System.out.println("目标文件所在目录不存在，准备创建它！");  
            if(!file.getParentFile().mkdirs()) {  
                System.out.println("创建目标文件所在目录失败！");  
                return false;  
            }  
        }  
        //创建目标文件  
        try {  
            if (file.createNewFile()) {  
                System.out.println("创建单个文件" + destFileName + "成功！");  
                return true;  
            } else {  
                System.out.println("创建单个文件" + destFileName + "失败！");  
                return false;  
            }  
        } catch (IOException e) {  
            e.printStackTrace();  
            System.out.println("创建单个文件" + destFileName + "失败！" + e.getMessage());  
            return false;  
        } 
	}
    
    /***
	 * 获取指定目录下的所有的文件（不包括文件夹），采用了递归
	 * 
	 * @param obj
	 * @return
	 */
	public List<File> getListFiles(Object obj) {
		File directory = null;
		if (obj instanceof File) {
			directory = (File) obj;
		} else {
			directory = new File(obj.toString());
		}
		ArrayList<File> files = new ArrayList<File>();
		if (directory.isFile()) {
			files.add(directory);
			return files;
		} else if (directory.isDirectory()) {
			File[] fileArr = directory.listFiles();
			for (int i = 0; i < fileArr.length; i++) {
				File fileOne = fileArr[i];
				files.addAll(getListFiles(fileOne));
			}
		}
		return files;
	}

	/**
	 * 按行读取文件内容
	 * @param file
	 */
	public void readFileByLines(File file) {
		System.out.println("配置文件名："+file.getName());
        BufferedReader reader = null;
        try {
//            System.out.println("以行为单位读取文件内容，一次读一整行：");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
            	if(tempString.equals("") || tempString.startsWith("#")){
            		continue;
            	}

                int index = tempString.indexOf("=");
                String key = tempString.substring(0, index).trim();
                String value = tempString.substring(index+1, tempString.length()).trim();
               
                line++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        	IOUtils.closeQuietly(reader); 
        }
    }
	
	
	 /**
	  * 判断文件是否存在
	  * @param filePath
	  * @return
	  */
    public boolean fileExists(String filePath)
    {
        File file = null;
        try
        {
            file = new File(filePath);
            if (file.exists())
            {
                return true;
            }
        }
        catch (Exception e)
        {}
        return false;
    }

    /**
     * 从指定文件中读取内容并以文本形式返回
     * 
     * @param path
     * @return
     */
    public String getFileString(String path)
    {
        StringBuilder buff = new StringBuilder();
        FileReader fr = null;
        // BufferedReader br = null;
        int readNum = 0;
        int len = 1024;
        char[] chars = new char[len];
        try
        {
            fr = new FileReader(path);
            // br = new BufferedReader(fr);
            while ((readNum = fr.read(chars, 0, len)) != -1)
            {
                buff.append(new String(chars, 0, readNum));
            }
        }
        catch (FileNotFoundException e)
        {
            log.error("getFileStringNotFound:" + path, e);
        }
        catch (IOException e)
        {
            log.error("getFileStringIO:" + path, e);
        }
        finally
        {
        	IOUtils.closeQuietly(fr); 
        }
        return buff.toString();
    }

    public byte[] getByteByStream(InputStream is)
    {
        byte[] datas = null;
        ByteArrayOutputStream baos = null;
        try
        {
            int size = 1024;
            byte[] tmp = new byte[size];
            baos = new ByteArrayOutputStream();
            for (int rSize = is.read(tmp, 0, size); rSize != -1; rSize = is
                .read(tmp, 0, size))
            {
                baos.write(tmp, 0, rSize);
            }
            datas = baos.toByteArray();
        }
        catch (IOException e)
        {
            log.error("getFileStringIO:", e);
            return new byte[0];
        }
        finally
        {
        	IOUtils.closeQuietly(baos); 
        	IOUtils.closeQuietly(is); 
            
        }
        return datas;
    }

    
    public List<String> getFileStringForLine(String filePath){
    	
    	List<String> lineList = new ArrayList<String>();
    	InputStream is = null;
    	BufferedReader br = null;
    	try {
    		is = new FileInputStream(filePath);
		    br = new BufferedReader(new InputStreamReader(is , "UTF-8"), 512);
			String theWord = null;
			do {
				theWord = br.readLine();
				if (theWord != null && !"".equals(theWord.trim())) {
					lineList.add(theWord);
				}
			} while (theWord != null);
			
		} catch (IOException e) {
			 log.error("getFileStringForLine:" + filePath, e);
		}finally{
			IOUtils.closeQuietly(is); 
			IOUtils.closeQuietly(br); 
		}
    	return lineList;
    }
    
    
    
    
    
    /**
     * 获取某个文件的大小
     * 
     * @param filePath
     * @return
     */
    public long getFileSize(String filePath)
    {
        File file = null;
        try
        {
            file = new File(filePath);
            if (file.exists())
            {
                return file.length();
            }
        }
        catch (Exception e)
        {}
        return 0;
    }

    public byte[] getFileBytes(String filePath)
    {
        byte[] bytes = new byte[0];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        FileInputStream fis = null;
        try
        {
            int len = 1024;
            int readlen = 0;
            byte[] tmpBytes = new byte[len];
            fis = new FileInputStream(filePath);
            while ((readlen = fis.read(tmpBytes, 0, len)) != -1)
            {
                baos.write(tmpBytes, 0, readlen);
            }
            bytes = baos.toByteArray();
        }
        catch (FileNotFoundException e)
        {
            log.error("getFileBytesNotFound:" + filePath, e);
        }
        catch (IOException e)
        {
            log.error("getFileBytesIO:" + filePath, e);
        }
        finally
        {
        	IOUtils.closeQuietly(baos); 
        	IOUtils.closeQuietly(fis); 
        }
        return bytes;

    }

    public void saveFile(String filePath, String content)
    {
    	OutputStreamWriter write = null;
    	BufferedWriter writer = null;
    	try {
			File f = new File(filePath);
			if (!f.exists()) {
				f.createNewFile();
			}
			 write = new OutputStreamWriter(
					new FileOutputStream(f), "UTF-8");
			writer = new BufferedWriter(write);
			writer.write(content);
			//writer.close();
		} catch (Exception e) {
			System.out.println("写文件内容操作出错");
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(write);
			IOUtils.closeQuietly(writer); 
		}
    }

    public int saveFile(String filePath, byte[] bytes)
    {
        int buflen = 1024;
        byte[] bufBytes = new byte[buflen];
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        FileOutputStream fos = null;
        try
        {
            createFile(filePath);
            fos = new FileOutputStream(filePath);
            for (int i = bais.read(bufBytes, 0, buflen); i != -1; i = bais
                .read(bufBytes, 0, buflen))
            {
                fos.write(bufBytes, 0, i);
            }
        }
        catch (FileNotFoundException e)
        {
            log.error("saveFileNOTFOUND:" + filePath, e);
        }
        catch (IOException e)
        {
            log.error("saveFileIO:" + filePath, e);
        }
        finally
        {
        	IOUtils.closeQuietly(fos); 
        	IOUtils.closeQuietly(bais); 
           
        }
        File file = new File(filePath);
        if (file.exists())
        {
            file = null;
            return 0;
        }
        else
        {
            file = null;
            return -1;
        }
    }

    public void addToFile(String savePath, String content)
    {
        FileWriter fw = null;
        try
        {
            createFile(savePath);
            fw = new FileWriter(savePath, true);
            fw.write(content);
        }
        catch (IOException e)
        {
            log.error("addToFile:" + savePath, e);
        }
        finally
        {
        	IOUtils.closeQuietly(fw);
        }
    }

    public void createFile(String filePath)
    {
        filePath = filePath.replaceAll("\\\\", "/");
        String parentPath = filePath.substring(0, filePath.lastIndexOf("/"));
        File parentFile = new File(parentPath);
        if (!parentFile.exists())
        {
            parentFile.mkdirs();
        }
        File file = new File(filePath);
        if (!file.exists())
        {
            try
            {
                file.createNewFile();
            }
            catch (IOException e)
            {
                log.error("createFile:" + filePath, e);
            }
        }
        file = null;
    }

    public void delFile(File file, long file_time)
    {
        if (!file.exists())
        {
            return;
        }
        long lastModified;
        lastModified = file.lastModified();
        if (file_time == 0)
        {
            file.delete();
        }
        else
        {
            if (lastModified < (new Date().getTime() - file_time))
            {
                file.delete();
            }
        }

    }

    public void delDir(String path, long file_time)
    {
        File dir = new File(path);
        if (dir.exists())
        {
            File file = null;
            File[] files = dir.listFiles();
            for (int index = 0; index < files.length; index++)
            {
                file = files[index];
                if (file.isDirectory())
                {
                    delDir(file.getAbsolutePath(), file_time);
                }
                else
                {
                    delFile(file, file_time);
                }
            }
            files = dir.listFiles();
            if (files.length == 0)
            {
                dir.delete();
            }
            file = null;
            files = null;
        }
        dir = null;
    }

    /**
     * 从源文件 拷贝到目标文件
     * 
     * @param srcpath
     * @param dstpath
     * @return 0 表示目标已存在 不拷贝；1表拷贝成功
     */
    public int copyFile(String srcpath, String dstpath)
    {
        int result = -9;
        // 检查参数合法
        if (srcpath == null || "".equals(srcpath))
        {
            result = -1;
        }

        if (dstpath == null || "".equals(dstpath))
        {
            result = -2;
        }

        File file = null;
        try
        {
            file = new File(dstpath);
            if (file.exists())
            {
                return 0;
            }
        }
        catch (Exception e)
        {}

        OutputStream out = null;
        InputStream in = null;

        try
        {
            in = new FileInputStream(srcpath);
            out = new FileOutputStream(dstpath);
            int bytesRead = -1;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) != -1)
            {
                out.write(buffer, 0, bytesRead);
            }
            result = 1;
        }
        catch (Exception ex)
        {
            log.error("Exception : ", ex);
        }
        finally
        {
        	IOUtils.closeQuietly(out);
			IOUtils.closeQuietly(in);
        }
        return result;
    }
    /**
     * 创建目录
     * @param folderPath
     */
    public static void mkdirs(String folderPath){
    	try{
    		File directory = new File(folderPath);
    		if(!directory.exists()){
    			directory.mkdirs();
    		}
    		
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
}
