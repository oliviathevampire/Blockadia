package net.thegaminghuskymc.authlib.properties;

import org.apache.commons.codec.binary.Base64;

import java.security.*;

public class Property {
    private final String name;
    private final String value;
    private final String signature;

    public Property(final String value, final String name) {
        this(value, name, null);
    }

    public Property(final String name, final String value, final String signature) {
        this.name = name;
        this.value = value;
        this.signature = signature;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public String getSignature() {
        return signature;
    }

    public boolean hasSignature() {
        return signature != null;
    }

    public boolean isSignatureValid(final PublicKey publicKey) {
        try {
            final Signature signature = Signature.getInstance("SHA1withRSA");
            signature.initVerify(publicKey);
            signature.update(value.getBytes());
            return signature.verify(Base64.decodeBase64(this.signature));
        } catch (final NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (final InvalidKeyException e) {
            e.printStackTrace();
        } catch (final SignatureException e) {
            e.printStackTrace();
        }
        return false;
    }
}