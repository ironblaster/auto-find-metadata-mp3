package main;

import org.apache.http.util.Args;
import org.junit.Test;
import org.omg.CORBA.ARG_OUT;

import config.Config;
import metadataWrite.WriteMeta;

public class Main {
	
	
	
	public static void main (String[] args) {
		
		
		
		
		Config.setApiaudiotag("");
		Config.setSongsDir("H:\\");
		
		WriteMeta.WriteMp3();
		
	}
	

}
