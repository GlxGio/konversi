package com.konversi;

import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import android.app.Activity;
import android.widget.Toast;

public class uraikonversiActivity extends DefaultHandler {
	
	private String tanggal = "";
	public ArrayList<String> kodekonversi = new ArrayList<String>();
	public ArrayList<Float> nilaikonversi = new ArrayList<Float>();
	private String src = "http://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml";
	
	public void settanggal(String tanggal) {
		this.tanggal=tanggal;

	}
	
	public String gettanggal() {
		return tanggal;
	}
	
	public uraikonversiActivity(Activity a) {
		XMLReader bacaxml = null;
		
		try {
			SAXParserFactory spfactory = SAXParserFactory.newInstance();
			spfactory.setValidating(false);
			SAXParser saxparser = spfactory.newSAXParser();
			bacaxml = saxparser.getXMLReader();
			bacaxml.setContentHandler(this);
			bacaxml.setErrorHandler(this);
			
			InputSource sumber = new InputSource(src);
			bacaxml.parse(sumber);
		} catch (Exception e) {
            Toast.makeText(a, "Tidak terhubung ke Internet", 10).show();
			}
	}
	
	public float getkodekonversi(String konvkode) {
        int ind = 0;
        if (konvkode.equalsIgnoreCase("EUR")) return 1;
        if (!(kodekonversi.contains(konvkode))) {
                return 0;
        } else {
                ind = kodekonversi.indexOf(konvkode);
                return nilaikonversi.get(ind);
        }
	}
    public float getnilaitukar(String kurs1, String kurs2){
        
        float c1, c2, c3;
        if (kurs1.equalsIgnoreCase("EUR")) c1=1; else c1 = getkodekonversi(kurs1);
        if (kurs2.equalsIgnoreCase("EUR")) c2=1; else c2 = getkodekonversi(kurs2);
        if(c1!=0) c3 = c2/c1; else c3=0;
        return c3;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName,
                    String list, Attributes atts) throws SAXException {

            if (list.equalsIgnoreCase("Cube")){

            		for (int att = 0; att < atts.getLength(); att++) {
                   		String kodeAtt = atts.getQName(att);
                   		if(kodeAtt.equalsIgnoreCase("currency"))
                   		kodekonversi.add(atts.getValue(kodeAtt));
                   		if(kodeAtt.equalsIgnoreCase("rate")){
                   			float f1 = Float.parseFloat(atts.getValue(kodeAtt));
                   			nilaikonversi.add(f1);
                   		}
                   		if(kodeAtt.equalsIgnoreCase("time"))
                   		settanggal(atts.getValue(kodeAtt));  
                		}
            }
    }
}
