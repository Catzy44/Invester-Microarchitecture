package me.catzy.invester.scraper.infrastructure.scraping.parser;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import me.catzy.invester.scraper.application.service.ScrapingService;

public class ArticleParser {
	private static final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	private static final Logger logger = LoggerFactory.getLogger(ScrapingService.class);
	
	public static NodeList loadDoc(URL url) throws ParserConfigurationException, IOException, SAXException {
		DocumentBuilder builder = factory.newDocumentBuilder();
		
        InputStream inputStream = url.openStream();
        Document document = builder.parse(inputStream);

        //normalising DOC
        document.getDocumentElement().normalize();
        NodeList items = document.getElementsByTagName("item");
        
        return items;
	}
	
	public static String getItem(Element element, String item) {
		NodeList nl = element.getElementsByTagName(item);
		if(nl.getLength() == 0) {
			return null;
		}
		return nl.item(0).getTextContent();
	}
	
	//multiple data formats support
	public static DateFormat[] formatters = new DateFormat[]{
		new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss X", Locale.ENGLISH),
		new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH),
		new SimpleDateFormat("MMM dd, yyyy HH:mm z", Locale.ENGLISH)
	};
	public static Date parseDate(String s) {
		for(DateFormat df : formatters) {
			try {return df.parse(s);} catch (ParseException e) {}
		}
		logger.error("failed parsing date: " + s);
		return null;
	}
}
