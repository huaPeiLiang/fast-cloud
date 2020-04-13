package com.fast.model;

import com.itextpdf.text.pdf.PdfSignatureAppearance;
import lombok.Data;

import java.security.PrivateKey;
import java.security.cert.Certificate;

@Data
public class SignatureInfo {
    private  String reason; //签名的原因，显示在pdf签名属性中
    private  String location;//签名的地点，显示在pdf签名属性中
    private String digestAlgorithm;//摘要算法名称，例如SHA-1
    private String imagePath;//图章路径
    private  String fieldName;//表单域名称
    private  Certificate[] chain;//证书链
    private  PrivateKey pk;//签名私钥
    private  int certificationLevel = 0; //批准签章
    private PdfSignatureAppearance.RenderingMode renderingMode;//表现形式：仅描述，仅图片，图片和描述，签章者和描述
    //图章属性
    private float rectllx ;//图章左下角x
    private float rectlly ;//图章左下角y
    private float recturx ;//图章右上角x
    private float rectury ;//图章右上角y
}
