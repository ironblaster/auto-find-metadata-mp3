package metadataWrite;

import java.io.File;

import com.mpatric.mp3agic.ID3v1Tag;
import com.mpatric.mp3agic.Mp3File;

import config.Config;
import metadataServiceRemote.AudiotagAPI;
import pojo.Metadata;

public class WriteMeta {
	



	public static void WriteMp3() {
		
		File dir = new File(Config.getSongsDir());
		  File[] directoryListing = dir.listFiles();
		  if (directoryListing != null) {
		    for (File child : directoryListing) {
		    
				System.out.println(child.getName());
	
		    
				if(child.getName().contains(".mp3"))
					try {
					//	Metadata values = new Metadata();
						
					Metadata values = AudiotagAPI.GetMetaAudio(child);
 
        	Mp3File mp3file = new Mp3File(child.getAbsoluteFile());
        	
        	
        	ID3v1Tag id3v1Tag = new ID3v1Tag();
        	  mp3file.setId3v1Tag(id3v1Tag);
        	  
        	  
        	  
        	  if (!values.getAuthor().isEmpty())
        	  id3v1Tag.setArtist(values.getAuthor());
        	  if (!values.getTitle().isEmpty())
        	  id3v1Tag.setTitle(values.getTitle());
        	  
        	  if(!values.getTitle().isEmpty())
        	mp3file.save(Config.getSongsDir()+values.getTitle()+" - "+values.getAuthor()+".mp3");
        	  else
        		  mp3file.save(Config.getSongsDir()+child.getName()+".mp3");
        	child.delete(); 
        }
        catch (Exception e) {
			e.printStackTrace();
		}
        
		   }}
		
		
		
	}

}
