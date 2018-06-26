/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.scms.modules.base.service.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import org.springframework.stereotype.Service;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections4.MapUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.entity.UserInfo;
import com.gimplatform.core.utils.BeanUtils;
import com.gimplatform.core.utils.RestfulRetUtils;
import com.gimplatform.core.utils.StringUtils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.scms.modules.base.service.ScmsPrintInfoService;
import com.scms.modules.base.entity.ScmsPrintInfo;
import com.scms.modules.base.repository.ScmsPrintInfoRepository;

@Service
public class ScmsPrintInfoServiceImpl implements ScmsPrintInfoService {
    
    protected static final Logger logger = LogManager.getLogger(ScmsPrintInfoServiceImpl.class);
	
    @Autowired
    private ScmsPrintInfoRepository scmsPrintInfoRepository;

	@Override
	public JSONObject getList(Pageable page, ScmsPrintInfo scmsPrintInfo, Map<String, Object> params) {
		List<Map<String, Object>> list = scmsPrintInfoRepository.getList(scmsPrintInfo, params, page.getPageNumber(), page.getPageSize());
		int count = scmsPrintInfoRepository.getListCount(scmsPrintInfo, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    ScmsPrintInfo scmsPrintInfo = (ScmsPrintInfo) BeanUtils.mapToBean(params, ScmsPrintInfo.class);
	    String content = MapUtils.getString(params, "content", "");
	    if(!StringUtils.isBlank(content)) {
	        JSONObject json = JSONObject.parseObject(content);
	        if(json != null && !StringUtils.isBlank(json.getString("qrCode"))) {
	            String qrCode = json.getString("qrCode");
                ByteArrayOutputStream os=new ByteArrayOutputStream();
                try {
                    //生成二维码，并转化成base64
                    BufferedImage qrImageBuffer = createQRImageBuffer(qrCode, 100, 100);
                    ImageIO.write(qrImageBuffer, "png", os);
                    Base64 base64 = new Base64();
                    String base64Img = new String(base64.encode(os.toByteArray()));
                    json.put("qrCodeBase64", base64Img);
                    scmsPrintInfo.setContent(json.toJSONString());
                } catch (Exception e) {
                    logger.error("二维码转化base64失败：", e);
                }
	        }
	    }
		scmsPrintInfoRepository.save(scmsPrintInfo);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        ScmsPrintInfo scmsPrintInfo = (ScmsPrintInfo) BeanUtils.mapToBean(params, ScmsPrintInfo.class);
		ScmsPrintInfo scmsPrintInfoInDb = scmsPrintInfoRepository.findOne(scmsPrintInfo.getId());
		if(scmsPrintInfoInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//合并两个javabean
		BeanUtils.mergeBean(scmsPrintInfo, scmsPrintInfoInDb);
		
		String content = scmsPrintInfoInDb.getContent();
        if(!StringUtils.isBlank(content)) {
            JSONObject json = JSONObject.parseObject(content);
            if(json != null && !StringUtils.isBlank(json.getString("qrCode"))) {
                String qrCode = json.getString("qrCode");
                ByteArrayOutputStream os=new ByteArrayOutputStream();
                try {
                    //生成二维码，并转化成base64
                    BufferedImage qrImageBuffer = createQRImageBuffer(qrCode, 100, 100);
                    ImageIO.write(qrImageBuffer, "png", os);
                    Base64 base64 = new Base64();
                    String base64Img = new String(base64.encode(os.toByteArray()));
                    json.put("qrCodeBase64", base64Img);
                    scmsPrintInfoInDb.setContent(json.toJSONString());
                } catch (Exception e) {
                    logger.error("二维码转化base64失败：", e);
                }
            }
        }
		scmsPrintInfoRepository.save(scmsPrintInfoInDb);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		String[] ids = idsList.split(",");
		for (int i = 0; i < ids.length; i++) {
			scmsPrintInfoRepository.delete(StringUtils.toLong(ids[i]));
		}
		return RestfulRetUtils.getRetSuccess();
	}
	
	/**
	 * 生成图片流二维码
	 * @param content
	 * @param width
	 * @param height
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
    private BufferedImage createQRImageBuffer(String content, int width, int height) throws  Exception{
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        Hashtable hints = new Hashtable();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hints);
        BufferedImage image = toBufferedImage(bitMatrix);
        return image;
    }

    private static final int black = 0xFF000000;
    private static final int white = 0xFFFFFFFF;

	private BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? black : white);
            }
        }
        return image;
    }
}
