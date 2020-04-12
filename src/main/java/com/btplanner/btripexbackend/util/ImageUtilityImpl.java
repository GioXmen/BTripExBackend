package com.btplanner.btripexbackend.util;

import org.apache.tomcat.util.codec.binary.Base64;

import java.io.IOException;
import java.io.InputStream;

public class ImageUtilityImpl {

    public String getBase64EncodedImage(String imageURL) throws IOException {
        java.net.URL url = new java.net.URL(imageURL);
        InputStream is = url.openStream();
        byte[] bytes = org.apache.commons.io.IOUtils.toByteArray(is);
        return Base64.encodeBase64String(bytes);
    }
}
