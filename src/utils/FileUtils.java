package utils;
import java.io.File;
import java.util.Vector;

public class FileUtils {
    public FileUtils(){}

    public Vector<File> getFolderFiles(String folderpath){
        File folder = new File(folderpath);
        Vector<File> files = new Vector<>();
        for(File file : folder.listFiles()){
            if(file.isFile()){
                files.add(file);
            }
        }
        return files;
    }

    public String getFileType(String filename){
        String[] stringTokens = filename.split("\\.");
        return  stringTokens[stringTokens.length - 1];
    }

    public Vector<File> getFolderFiles(String folderpath, String format){
        File folder = new File(folderpath);
        Vector<File> files = new Vector<>();
        for(File file : folder.listFiles()){
            if(file.isFile()){
                if(getFileType(file.getName()).equals(format)){
                    files.add(file);
                }
            }
        }
        return files;
    }

    public Vector<File> getFolderFilesRecursive(String folderpath){
        File folder = new File(folderpath);
        Vector<File> files = new Vector<>();
        for(File file : folder.listFiles()){
            if(file.isFile()){
                files.add(file);
            }else{
                Vector<File> temp = getFolderFilesRecursive(file.getAbsolutePath());
                files.addAll(temp);
            }
        }

        return files;
    }

    public Vector<File> getFolderFilesRecursive(String folderpath, String format){
        File folder = new File(folderpath);
        Vector<File> files = new Vector<>();
        for(File file : folder.listFiles()){
            if(file.isFile()){
                if(getFileType(file.getName()).equals(format)){
                    files.add(file);
                }
            }else{
                Vector<File> temp = getFolderFilesRecursive(file.getAbsolutePath(), format);
                files.addAll(temp);
            }
        }

        return files;
    }


}
