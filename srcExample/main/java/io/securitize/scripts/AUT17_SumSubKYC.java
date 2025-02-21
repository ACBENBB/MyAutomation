package io.securitize.scripts;

import io.securitize.infra.api.sumsub.SumSubAPI;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class AUT17_SumSubKYC {
    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException, IOException {
        String applicantID = SumSubAPI.getApplicantId("64a4d70dd7405dd60c9e8029");
        String applicantStatus = SumSubAPI.getApplicantStatus(applicantID);
        String response = SumSubAPI.setApplicantTestComplete(applicantID);
        System.out.println(response);
        applicantStatus = SumSubAPI.getApplicantStatus(applicantID);
    }
}