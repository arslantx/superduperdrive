package com.udacity.jwdnd.course1.cloudstorage.services;

import java.util.List;
import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.util.EncoderUtil;
import org.springframework.stereotype.Service;

@Service
public class CredentialService {
    
    private CredentialMapper credentialMapper;
    private EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper,
            EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public List<Credential> getAllCredentials(Integer userid) {
        List<Credential> credentials = credentialMapper.getAllCredentials(userid);
        decryptPasswords(credentials);
        return credentials;
    }

    public void createOrEditCredential(Credential credential) {
        String encodedKey = EncoderUtil.getRandomEncodedStr();
        credential.setKey(encodedKey);
        String encryptedPwd = encryptionService.encryptValue(credential.getDecryptedPassword(), encodedKey);
        credential.setPassword(encryptedPwd);
        // credentialid is null for new credentials
        if (credential.getCredentialid() == null) {
            credentialMapper.insert(credential);
        } else {
            credentialMapper.update(credential);
        }
    }

    public void deleteCredential(Integer credentialid, Integer userid) {
        credentialMapper.delete(credentialid, userid);
    }

    private void decryptPasswords(List<Credential> credentials) {
        for (Credential credential : credentials) {
            String key = credential.getKey();
            String encryptedPwd = credential.getPassword();
            String decryptedPwd = encryptionService.decryptValue(encryptedPwd, key);
            credential.setDecryptedPassword(decryptedPwd);
        }
    }
}
