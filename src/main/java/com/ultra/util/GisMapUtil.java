package com.ultra.util;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * 处理和底图相关方法
 * 
 */
public class GisMapUtil {

    public final static String FILE_SVG = "svg";

    public final static String FILE_GEOJSON = "geojson";

    public final static String FILE_PNG = "png";
    public final static String FILE_JPEG = "jpeg";
    public final static String FILE_JPG = "jpg";

    public final static short TYPE_UNKNOW = 0;
    public final static short TYPE_SVG = 1;
    public final static short TYPE_IMAGE = 2;
    public final static short TYPE_GEOJSON = 3;

    /**
     * 读取文件获取底图坐标范围
     * 
     * @param fileName
     * @return
     */
    public static Double[] getExtents(String fileName) {
        File file = null;
        if (null != fileName && !"".equals(fileName.trim())) {
            file = new File(fileName);
            if (file.exists()) {
                int type = getFileType(fileName);
                switch (type) {
                case TYPE_SVG:
                    return svgHandler(file);
                case TYPE_IMAGE:
                    return imageHandler(file);
                case TYPE_GEOJSON:
                    return geojsonHandler(file);
                }
            }
        }
        return null;
    }

    public static short getFileType(String fileName) {
        if (fileName.endsWith(FILE_SVG) || fileName.endsWith(FILE_SVG.toUpperCase())) {
            return TYPE_SVG;
        } else if (fileName.endsWith(FILE_GEOJSON) || fileName.endsWith(FILE_GEOJSON.toUpperCase())) {
            return TYPE_GEOJSON;
        } else if (fileName.endsWith(FILE_PNG) || fileName.endsWith(FILE_PNG.toUpperCase())
                || fileName.endsWith(FILE_JPEG) || fileName.endsWith(FILE_JPEG.toUpperCase())
                || fileName.endsWith(FILE_JPG) || fileName.endsWith(FILE_JPG.toUpperCase())) {
            return TYPE_IMAGE;
        }
        return TYPE_UNKNOW;
    }

    public static Double[] imageHandler(File file) {
        Double[] extents = new Double[4];
        BufferedImage bufferedImage;
        try {
            bufferedImage = ImageIO.read(file);
            int width = bufferedImage.getWidth();
            int height = bufferedImage.getHeight();
            extents[0] = 0d;
            extents[1] = 0d;
            extents[2] = Math.floor(width * 1.0);
            extents[3] = Math.floor(height * 1.0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return extents;
    }

    public static Double[] geojsonHandler(File file) {
        return null;
    }

    /**
     * 根据文件获取底图坐标范围 备用方法
     * 
     * @param file
     * @return
     */
    private static Double[] svgHandlerEx(File file) {
        DocumentBuilderFactory factory = null;
        DocumentBuilder builder = null;
        Document doc = null;
        String extentStr = "";
        Double[] extents = new Double[4];
        try {
            factory = DocumentBuilderFactory.newInstance();
            builder = factory.newDocumentBuilder();
            doc = builder.parse(file);
            XPathFactory xfactory = XPathFactory.newInstance();
            XPath xpath = xfactory.newXPath();
            Node node = (Node) xpath.evaluate("/svg", doc, XPathConstants.NODE);
            Node width = node.getAttributes().getNamedItem("width");
            Node height = node.getAttributes().getNamedItem("height");
            if (null != width && null != height) {
                String widthStr = width.getNodeValue().replaceAll("px", "");
                String heightStr = height.getNodeValue().replaceAll("px", "");
                extents[0] = 0d;
                extents[1] = 0d;
                extents[2] = Double.parseDouble(widthStr);
                extents[3] = Double.parseDouble(heightStr);
            } else {
                extentStr = node.getAttributes().getNamedItem("viewBox").getNodeValue();
                extents = convert(extentStr);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            if (null != doc) {
                doc = null;
            }
        }
        return extents;
    }

    private static Double[] convert(String extentStr) {
        Double[] extents = new Double[4];
        String[] extent = extentStr.split(" ");
        for (int i = 0; i < extents.length; i++) {
            extents[i] = Double.valueOf(extent[i]);
        }
        return extents;
    }

    private static Double[] svgHandler(File file) {
        Double[] extents = null;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tmpStr = null;
            while ((tmpStr = reader.readLine()) != null) {
                String widthTok = "width=\"";
                String heightTok = "height=\"";
                if (tmpStr.contains(widthTok) && tmpStr.contains(heightTok)) {// 方案一
                    String widthStr = tmpStr.substring(tmpStr.indexOf(widthTok) + widthTok.length());
                    widthStr = widthStr.substring(0, widthStr.indexOf("\"")).replaceAll("px", "");
                    String heightStr = tmpStr.substring(tmpStr.indexOf(heightTok) + heightTok.length());
                    heightStr = heightStr.substring(0, heightStr.indexOf("\"")).replaceAll("px", "");
                    if (!widthStr.contains("%")) {
                        extents = new Double[4];
                        extents[0] = 0d;
                        extents[1] = 0d;
                        extents[2] = Double.parseDouble(widthStr);
                        extents[3] = Double.parseDouble(heightStr);
                        break;
                    }
                } else {// 方案2
                    if (tmpStr.contains("viewBox")) {
                        tmpStr = tmpStr.substring(tmpStr.indexOf("viewBox=\"") + "viewBox=\"".length());
                        tmpStr = tmpStr.substring(0, tmpStr.indexOf("\""));
                        extents = convert(tmpStr);
                        break;
                    }
                }
            }

            if (null == extents) {// 使用备用方案，耗时较长
                extents = svgHandlerEx(file);
            }

        } catch (Throwable e) {
            e.printStackTrace();
            extents = svgHandlerEx(file);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                }
            }
        }
        return extents;
    }

    public static void main(String[] args) {
        Double[] result = GisMapUtil.getExtents("D:/test.svg");
        for (int i = 0; i < result.length; i++) {
            System.out.println(result[i]);
        }

        /*
         * result = GisMapUtil.getExtents("D:/bg.jpg"); for (int i = 0; i <
         * result.length; i++) { System.out.println(result[i]); }
         */

    }
}
