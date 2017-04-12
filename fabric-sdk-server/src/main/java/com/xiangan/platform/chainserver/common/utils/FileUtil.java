package com.xiangan.platform.chainserver.common.utils;

import com.google.protobuf.ByteString;
import com.xiangan.platform.chainserver.common.domain.FileDataRequest;
import com.xiangna.www.protos.common.Common;
import com.xiangna.www.protos.contract.ContractRequest;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * 文件处理工具类
 *
 * @Creater liuzhudong
 * @Date 2017/4/11 19:26
 * @Version 1.0
 * @Copyright
 */
public class FileUtil {

    /**
     * 文件解码成bytestring
     *
     * @param data
     * @return
     * @throws IOException
     */
    public static ByteString decoderToByteString(String data) throws IOException {
        return ByteString.copyFrom(decoder(data));
    }

    /**
     * 文件解码
     *
     * @param data
     * @return
     * @throws IOException
     */
    public static byte[] decoder(String data) throws IOException {
        BASE64Decoder decoder = new BASE64Decoder();
        return decoder.decodeBuffer(data);
    }

    /**
     * 文件编码
     *
     * @param data
     * @return
     */
    public static String encoder(byte[] data) {
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }

    /**
     * 接口请求参数转换成chaincode执行参数
     *
     * @param contractNO
     * @param requests
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    public static ContractRequest.FileAddRequest convertData(String contractNO, List<FileDataRequest> requests) throws IOException, NoSuchAlgorithmException {
        if (requests == null) {
            return null;
        }
        ContractRequest.FileAddRequest.Builder builder = ContractRequest.FileAddRequest.newBuilder();
        builder.setNo(contractNO);

        Common.LedgerFileData fileData;
        for (FileDataRequest request : requests) {
            fileData = decoderFileData(request);
            builder.addFileDatas(fileData);
            builder.addFileInfos(Common.LedgerFile.newBuilder()
                    .setName(request.getName())
                    .setUrl(request.getUrl())
                    .setSha256(fileData.getKey())
                    .setType(request.getType())
                    .setIsEncrypted(request.isEncrypted())
                    .setSize(request.getSize())
                    .addAllAppIds(request.getAppIDs())
                    .build());
        }
        return builder.build();
    }

    /**
     * 格式化成文件数据
     *
     * @param fileDataRequest
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    public static Common.LedgerFileData decoderFileData(FileDataRequest fileDataRequest) throws IOException, NoSuchAlgorithmException {
        byte[] data = decoder(fileDataRequest.getData());
        String sha256 = AESUtil.sha256(data);
        return Common.LedgerFileData.newBuilder()
                .setKey(sha256)
                .setData(ByteString.copyFrom(data))
                .build();

    }

    public static void main(String[] args) throws Exception {
        File file = new File("/Users/liuzhudong/Downloads/oa.proto");
        InputStream in = new FileInputStream(file);
        byte[] data = new byte[in.available()];
        in.read(data);
        in.close();
        String sha256 = AESUtil.sha256(data);
        System.out.println(sha256);
    }
}
