package com.home.uploadingfiles;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.print.DocFlavor.STRING;


@Controller
public class UploadingController {
    public static final String uploadingdir = System.getProperty("user.dir") + "/uploadingdir/";
   // public static final String uploadingdir = "E:\\tempFolder\\"+"/uploadingdir/";
    public static final String directoryWindows ="E://tempFolder";
    
    
    @RequestMapping("/")
    public String uploading(Model model) {
        File file = new File(uploadingdir);
        model.addAttribute("files", file.listFiles());
        return "uploading";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String uploadingPost(@RequestParam("uploadingFiles") MultipartFile[] uploadingFiles,
    							@RequestParam("somevalue") File fname ,
    							@RequestParam("uploadingFiles") MultipartFile uploadingFiles1
    							) throws IOException {
    	
        for(MultipartFile uploadedFile : uploadingFiles) {
        
            File file = new File(uploadingdir+ uploadedFile.getOriginalFilename());
            uploadedFile.transferTo(file);
        }
        String filename =fname.getName();
	    System.out.println(filename);
        File folder=new File("E:\\tempFolder\\"+filename+"\\");
           
       folder.mkdirs();
		
    	//System.out.println(folder);

  	byte[] bytes=uploadingFiles1.getBytes();

   	Path path = Paths.get("E:\\tempFolder\\"+filename+"\\"+ uploadingFiles1.getOriginalFilename());
    	Files.write(path, bytes);
    
  	    return "redirect:/";
    }
    
    @RequestMapping("/delete")
	public String delete(Model model)
	{
		return "deleteFile";
	}
	
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String deleteFile (@RequestParam("somefile") File filetodelete,  @RequestParam("somefile1") File fileto) throws IOException
	{
    	System.out.println(filetodelete);
		System.out.println(fileto);
		
		String file1 =filetodelete.getName();
		String file2 =fileto.getName();
		
		
	Path path1 = Paths.get("E:\\tempFolder\\"+file1+"\\"+file2);
	Path path2 = Paths.get(System.getProperty("user.dir") + "/uploadingdir/"+"\\"+file2);
		
		if (Files.exists(path1))		{
			System.out.println("exists");
		}
		else 
		{
			System.out.println("not exists");
		}
	
		if(Files.exists(path2)){
		System.out.println("file exists in path2");
	}
	else{
		System.out.println("file doesnot exist in path2");
	}
		
	File file=new File("E:\\tempFolder\\"+file1+"\\"+file2);
   	System.out.println("file found"+file);
   	file.delete();
   	
   	File fileof=new File(System.getProperty("user.dir") + "/uploadingdir/"+"\\"+file2);
   	System.out.println(fileof);
   	fileof.delete();
  
   	//java.io.File.delete();
	 return "redirect:/";
	}
}

