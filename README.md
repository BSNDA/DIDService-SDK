## did-sdk调用参考


## 特征


- 使用Java开源框架实现，能很轻松的集成到你的项目或产品中 


## 快速接入


- 下载SDK的源码编译打包成jar，命名为did-sdk-1.0.jar

- 将did-sdk-1.0.jar添加到项目工程的classpath目录下


## did-sdk的入口类DidClient介绍

DidClient初始化代码示例 

/**
*
* @param url 连接服务的地址
* @param projectId 项目id
* @param token 项目key
*/
public DidClient(String url,String projectId,String token) {
    didService = new DidService(url, token, projectId);
    authIssuerService = new AuthIssuerService(url, token, projectId);
    credentialService = new CredentialService(url, token, projectId);
    hubService = new HubService(url, token, projectId);
}

## 调用接口

这里以创建did为例子，其他接口与之相同，只需要传入相应的参数即可,方法内部实现签名操作

1.获取DidClient对象

DidClient didClient = new DidClient(url,projectId,token);

2.调用创建did的方法

DidDataWrapper didDataWrapper = didClient.createDid(true);

## 创建did代码示例
/**
*
* @param isStorageOnChain 是否上链
* @return
* @description 创建did
*/
public DidDataWrapper createDid(Boolean isStorageOnChain) {
    ResultData<DidDataWrapper> genDidResult = null;
    try {
        genDidResult = didService.generateDid(isStorageOnChain);
    } catch (DidException e) {
        throw e;
    } catch (Exception e) {
        e.printStackTrace();
        throw new DidException("create did failed:"+e.getMessage());
    }
     if(!genDidResult.isSuccess()) {
        throw new DidException(genDidResult.getMsg());
     }
     return genDidResult.getData();
}



## 签名算法介绍

采用FISCO-BCOS secp256K1算法

## 签名算法示例

### 签名

/**
* Sign the message according to the private key
* @param message 需要签名的字符串
* @param privateKey 私钥
*/
public static String sign(String message, String privateKey) throws Exception {
    SignatureData sigData = secp256k1SignToSignature(message, new BigInteger(privateKey));
    return secp256k1SigBase64Serialization(sigData);
}
  
### 验签

/**
* Verify the signature of the message according to the public key
* @param message 签名的字符串
* @param publicKey 公钥
* @param signValue 签名字符串
*/
public static boolean verify(String message, String publicKey, String signValue) throws Exception {
    SignatureData sigData =
    secp256k1SigBase64Deserialization(signValue);
    byte[] hashBytes = Hash.sha3(message.getBytes());
    ECDSASign ecdsaSign = new ECDSASign();
    return ecdsaSign.secp256Verify(hashBytes, new BigInteger(publicKey), sigData);
}
  
##辅助功能介绍

did-sdk里面有丰富工具类用来生成密钥对，非对称加解密，加签与验签，数据格式转换等功能。

这里以生成密钥对作为示例：

KeyPair keyPair = ECDSAUtils.createKey();

/**
* Generate public and private keys
*/
public static com.reddate.did.sdk.protocol.common.KeyPair createKey() throws Exception{
    com.reddate.did.sdk.protocol.common.KeyPair keyPair = new com.reddate.did.sdk.protocol.common.KeyPair();
    ECKeyPair keyPairOriginal = Keys.createEcKeyPair();
    keyPair.setPublicKey(keyPairOriginal.getPublicKey().toString());
    keyPair.setPrivateKey(keyPairOriginal.getPrivateKey().toString());
    keyPair.setType(ECDSAUtils.TYPE);
    return keyPair;
}

  
## Maven


```
<dependency>
  <groupId>com.reddate</groupId>
  <artifactId>did.sdk</artifactId>
  <version>${version}</version>
</dependency>
```

## Gradle


```
compile ('com.reddate:did.sdk:${version}')
```

	
	
 	
	