package com.fast.util;

import com.fast.enums.ReplacePdfEnum;
import com.fast.model.SignatureInfo;
import com.itextpdf.text.*;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfSignatureAppearance;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.security.*;
import org.springframework.util.Assert;

import java.io.*;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.util.UUID;


public class PdfUtil {

    public static final char[] PASSWORD = "123456".toCharArray();// keystory密码

    // pdf 加水印
    public static void manipulatePdf(String src, String dest, String waterText) throws IOException, DocumentException {
        PdfReader reader = new PdfReader(src);
        int pages = reader.getNumberOfPages();

        PdfStamper stamper = new PdfStamper(reader,new FileOutputStream(dest));

        // 加密
        String password = String.valueOf(Math.abs(waterText.hashCode()));
        stamper.setEncryption(null,password.getBytes(), PdfWriter.ALLOW_PRINTING,PdfWriter.STANDARD_ENCRYPTION_40);

        BaseColor color = new BaseColor(42, 183, 248);
        Font f = new Font(Font.FontFamily.HELVETICA, 20,1,color);
        new Font();
        PdfContentByte over = stamper.getOverContent(3);

        // 将水印文字变长
        for (int i=0; i<10; i++){
            waterText += ("     "+waterText);
        }

        Phrase p = new Phrase(waterText, f);

        // 已第一页来计算水印
        Rectangle pageSize = reader.getPageSize(1);
        float width = pageSize.getWidth();
        float height = pageSize.getHeight();
        int v = (int)(width+height) / 180;

        for (int i=1; i<=pages; i++){
            over = stamper.getOverContent(i);
            over.saveState();
            PdfGState gs1 = new PdfGState();
            gs1.setFillOpacity(0.5f);
            over.setGState(gs1);
            int x = (int)height/2*-1;
            for (int j=0; j<v; j++){
                x += 180;
                ColumnText.showTextAligned(over, Element.ALIGN_CENTER, p, x, (height/2), 45);
            }
            over.restoreState();
        }
        stamper.close();
        reader.close();
    }

    // pdf 替换信息
    public static String replacePdfContract(String pdfFile, String key, String value, String type) throws Exception{
        Assert.hasText(pdfFile, "pdfFile is required");
        Assert.hasText(key, "key is required");
        Assert.hasText(value, "value is required");
        Assert.hasText(type, "type is required");
        com.itextpdf.text.pdf.PdfReader reader = new com.itextpdf.text.pdf.PdfReader(pdfFile);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        com.itextpdf.text.pdf.PdfStamper stamper = new com.itextpdf.text.pdf.PdfStamper(reader, baos);
        com.itextpdf.text.pdf.AcroFields acroForm = stamper.getAcroFields();
        if (acroForm != null) {
            stamper.setFormFlattening(true);
            if (type.equals(ReplacePdfEnum.input.name())){
                acroForm.setField(key, value);
                stamper.partialFormFlattening(key);
            }else if (type.equals(ReplacePdfEnum.radio.name())){
                acroForm.setField(key+value, "是");
                stamper.partialFormFlattening(key);
            }
        }
        stamper.close();
        reader.close();
        // 替换后的pdf输出地址
        String file = "/"+ UUID.randomUUID().toString()+".pdf";
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(baos.toByteArray());
        fos.close();
        baos.close();
        return file;
    }

    // 电子签章
    public static void sign(String src, String target, SignatureInfo signatureInfo) {
        InputStream inputStream = null;
        FileOutputStream outputStream = null;
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        try {
            inputStream = new FileInputStream(src);
            ByteArrayOutputStream tempArrayOutputStream = new ByteArrayOutputStream();
            PdfReader reader = new PdfReader(inputStream);
            // 创建签章工具PdfStamper ，最后一个boolean参数是否允许被追加签名
            // false的话，pdf文件只允许被签名一次，多次签名，最后一次有效
            // true的话，pdf可以被追加签名，验签工具可以识别出每次签名之后文档是否被修改
            PdfStamper stamper = PdfStamper.createSignature(reader,
                    tempArrayOutputStream, '\0', null, true);
            // 获取数字签章属性对象
            PdfSignatureAppearance appearance = stamper
                    .getSignatureAppearance();
            appearance.setReason(signatureInfo.getReason());
            appearance.setLocation(signatureInfo.getLocation());
            // 设置签名的位置，页码，签名域名称，多次追加签名的时候，签名预名称不能一样 图片大小受表单域大小影响（过小导致压缩）
            // 签名的位置，是图章相对于pdf页面的位置坐标，原点为pdf页面左下角
            // 四个参数的分别是，图章左下角x，图章左下角y，图章右上角x，图章右上角y
            appearance.setVisibleSignature(
                    new Rectangle(signatureInfo.getRectllx(), signatureInfo
                            .getRectlly(), signatureInfo.getRecturx(),
                            signatureInfo.getRectury()), 1, signatureInfo
                            .getFieldName());
            // 读取图章图片
            Image image = Image.getInstance(signatureInfo.getImagePath());
            appearance.setSignatureGraphic(image);
            appearance.setCertificationLevel(signatureInfo
                    .getCertificationLevel());
            // 设置图章的显示方式，如下选择的是只显示图章（还有其他的模式，可以图章和签名描述一同显示）
            appearance.setRenderingMode(signatureInfo.getRenderingMode());
            // 摘要算法
            ExternalDigest digest = new BouncyCastleDigest();
            // 签名算法
            ExternalSignature signature = new PrivateKeySignature(
                    signatureInfo.getPk(), signatureInfo.getDigestAlgorithm(),
                    null);
            // 调用itext签名方法完成pdf签章 //数字签名格式，CMS,CADE
            MakeSignature.signDetached(appearance, digest, signature,
                    signatureInfo.getChain(), null, null, null, 0,
                    MakeSignature.CryptoStandard.CADES);

            inputStream = new ByteArrayInputStream(
                    tempArrayOutputStream.toByteArray());
            // 定义输入流为生成的输出流内容，以完成多次签章的过程
            result = tempArrayOutputStream;

            outputStream = new FileOutputStream(new File(target));
            outputStream.write(result.toByteArray());
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != outputStream) {
                    outputStream.close();
                }
                if (null != inputStream) {
                    inputStream.close();
                }
                if (null != result) {
                    result.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        try {
            PdfUtil app = new PdfUtil();
            // 将证书文件放入指定路径，并读取keystore ，获得私钥和证书链
            String pkPath = "D:/server.p12";
            KeyStore ks = KeyStore.getInstance("PKCS12");
            ks.load(new FileInputStream(pkPath), PASSWORD);
            String alias = ks.aliases().nextElement();
            PrivateKey pk = (PrivateKey) ks.getKey(alias, PASSWORD);
            // 得到证书链
            Certificate[] chain = ks.getCertificateChain(alias);
            //需要进行签章的pdf
            String path = "D:/demo.pdf";
            // 封装签章信息
            SignatureInfo signInfo = new SignatureInfo();
            signInfo.setReason("理由");
            signInfo.setLocation("位置");
            signInfo.setPk(pk);
            signInfo.setChain(chain);
            signInfo.setCertificationLevel(PdfSignatureAppearance.NOT_CERTIFIED);
            signInfo.setDigestAlgorithm(DigestAlgorithms.SHA1);
            signInfo.setFieldName("demo");
            // 签章图片
            signInfo.setImagePath("d:/sign.jpg");
            signInfo.setRenderingMode(PdfSignatureAppearance.RenderingMode.GRAPHIC);
            signInfo.setRectllx(100);  // 值越大，代表向x轴坐标平移 缩小 （反之，值越小，印章会放大）
            signInfo.setRectlly(200);  // 值越大，代表向y轴坐标向上平移（大小不变）
            signInfo.setRecturx(400);  // 值越大   代表向x轴坐标向右平移  （大小不变）
            signInfo.setRectury(100);  // 值越大，代表向y轴坐标向上平移（大小不变）
            //签章后的pdf路径
            app.sign(path, "D:/demo3.pdf", signInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
