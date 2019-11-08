package metadataWrite;

import java.io.File;

import com.mpatric.mp3agic.ID3v1Tag;
import com.mpatric.mp3agic.Mp3File;

import config.Config;

public class WriteMeta {
	
	public static String SongsDir ="";

	
	public static void WriteMp3() {
		
		
	//NOT VALID TODO	
		
		File dir = new File(Config.getSongsDir());
		  File[] directoryListing = dir.listFiles();
		  if (directoryListing != null) {
		    for (File child : directoryListing) {
		    
				System.out.println(child.getName());
	
		    
				if(child.getName().contains(".mp3"))
       
					
					try {

 
        	Mp3File mp3file = new Mp3File(child.getAbsoluteFile());
        	
        	
        	ID3v1Tag id3v1Tag = new ID3v1Tag();
        	  mp3file.setId3v1Tag(id3v1Tag);
        	  
        	  id3v1Tag.setArtist(file[0]);
        	  id3v1Tag.setTitle(file[1]);
        	  
        	  
        	mp3file.save(Config.getSongsDir()+file[0]+" - "+file[1]+".mp3");
        	child.delete(); 
        }
        catch (Exception e) {
			e.printStackTrace();
		}
        
		   }}
		
		
		
	}

}
