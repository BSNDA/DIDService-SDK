package com.reddate.did.sdk.util;

import com.google.common.collect.ImmutableList;
import com.reddate.did.sdk.constant.ErrorMessage;
import com.reddate.did.sdk.exception.DidException;
import com.reddate.did.sdk.protocol.common.KeyPair;
import com.reddate.did.sdk.util.ECDSAUtils;

import org.bitcoinj.crypto.*;
import org.web3j.crypto.ECKeyPair;

import java.util.List;

public class MnemonicUtil {

	private final static ImmutableList<ChildNumber> BIP44_ETH_ACCOUNT_ZERO_PATH = ImmutableList
			.of(new ChildNumber(44, true), new ChildNumber(60, true), ChildNumber.ZERO_HARDENED, ChildNumber.ZERO);

	public static KeyPair generalKeyPair(List<String> str) {
		try {
			// seed
			byte[] seed = MnemonicCode.toSeed(str, "");

			// master key
			DeterministicKey masterPrivateKey = HDKeyDerivation.createMasterPrivateKey(seed);
			DeterministicHierarchy deterministicHierarchy = new DeterministicHierarchy(masterPrivateKey);

			// child key
			DeterministicKey deterministicKey = deterministicHierarchy.deriveChild(BIP44_ETH_ACCOUNT_ZERO_PATH, false,
					true, new ChildNumber(0));
			byte[] bytes = deterministicKey.getPrivKeyBytes();
			ECKeyPair keyPair = ECKeyPair.create(bytes);

			KeyPair keyPairInfo = new KeyPair();
			keyPairInfo.setPrivateKey(keyPair.getPrivateKey().toString());
			keyPairInfo.setPublicKey(keyPair.getPublicKey().toString());
			keyPairInfo.setType(ECDSAUtils.TYPE);
			return keyPairInfo;
		} catch (Exception e) {
			throw new DidException(ErrorMessage.CREAT_KEY_FAIL.getCode(), ErrorMessage.CREAT_KEY_FAIL.getMessage() + ": "+ e.getMessage());
		}
	}
}
