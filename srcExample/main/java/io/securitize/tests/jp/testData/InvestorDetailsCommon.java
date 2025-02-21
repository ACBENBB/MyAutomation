package io.securitize.tests.jp.testData;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import lombok.Data;

import java.io.FileReader;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static io.securitize.infra.reporting.MultiReporter.errorAndStop;

@Data
public class InvestorDetailsCommon {
    protected Random random = new SecureRandom();
    protected String familyName;
    protected String givenName;
    protected String familyNameKana;
    protected String givenNameKana;
    protected String birthYear;
    protected String birthMonth;
    protected String birthDay;
    protected String gender;
    protected String telephone;
    protected String postalCode;
    protected String prefecture;
    protected String city;
    protected String street;
    protected String building;
    protected String email;

    /**
     *  nameCsvFile (no header) format -- 2 columns as follows: {katakana name},{kanji name}
     *  read all lines in nameCsvFile and return random name (lastName or firstName) in [0]:katakana, [1]:kanji
     */
    protected String[] getNameRandomlyFromCsvFile(String nameCsvFile, Random random) {
        List<String[]> data = new ArrayList<>();
        try (CSVReader csvReader = new CSVReaderBuilder(new FileReader(nameCsvFile)).withSkipLines(0).build()) {
            data = csvReader.readAll();
        } catch (Exception exception) {
            errorAndStop("failed to get name from " + nameCsvFile + ", " + exception, false);
        }
        int randomNumber = random.nextInt(data.size()); // random number from 0 to data.size()
        return data.get(randomNumber);
    }

    protected String getRandomValueFromArray(String[] list, Random random) {
        List<String> data = Arrays.asList(list);
        int randomNumber = random.nextInt(data.size()); // random number from 0 to data.size()
        return data.get(randomNumber);
    }

    // 県庁所在地
    protected String getAddressRandomly(Random random) {
        String[] addressList = {
                "0608588,0112314111,北海道,札幌市中央区,北3条西6丁目",
                "0308570,0177221111,青森県,青森市,長島1-1-1",
                "0208570,0196513111,岩手県,盛岡市,内丸10-1",
                "9808570,0222112111,宮城県,仙台市青葉区,本町3-8-1",
                "0108570,0188601111,秋田県,秋田市,山王4-1-1",
                "9908570,0236302211,山形県,山形市,松波2-8-1",
                "9608670,0245211111,福島県,福島市,杉妻町2-16",
                "3108555,0293011111,茨城県,水戸市,笠原町978-6",
                "3208501,0286232323,栃木県,宇都宮市,塙田1-1-20",
                "3718570,0272231111,群馬県,前橋市,大手町1-1-1",
                "3309301,0488242111,埼玉県,さいたま市浦和区,高砂3-15-1",
                "2608667,0432232110,千葉県,千葉市,中央区市場町1-1",
                "1638001,0353211111,東京都,新宿区,西新宿2-8-1",
                "2318588,0452101111,神奈川県,横浜市中区,日本大通1",
                "9508570,0252855511,新潟県,新潟市中央区,新光町4-1",
                "9308501,0764314111,富山県,富山市,新総曲輪1-7",
                "9208580,0762251111,石川県,金沢市,鞍月1-1",
                "9108580,0776211111,福井県,福井市,大手3-17-1",
                "4008501,0552371111,山梨県,甲府市,丸の内1-6-1",
                "3808570,0262320111,長野県,長野市,大字南長野字幅下692-2",
                "5008570,0582721111,岐阜県,岐阜市,薮田南2-1-1",
                "4208601,0542212455,静岡県,静岡市葵区,追手町9-6",
                "4608501,0529612111,愛知県,名古屋市中区,三の丸3-1-2",
                "5148570,0592243070,三重県,津市,広明町13番地",
                "5208577,0775283993,滋賀県,大津市,京町4-1-1",
                "6028570,0754518111,京都府,京都市上京区,下立売通新町西入薮ノ内町",
                "5408570,0669410351,大阪府,大阪市中央区,大手前2丁目",
                "6508567,0783417711,兵庫県,神戸市中央区,下山手通5-10-1",
                "6308501,0742221101,奈良県,奈良市,登大路町30",
                "6408585,0734324111,和歌山県,和歌山,市小松原通1-1",
                "6808570,0857267111,鳥取県,鳥取市,東町1-220",
                "6908501,0852225111,島根県,松江市,殿町１番地",
                "7008570,0862242111,岡山県,岡山市北区,内山下2-4-6",
                "7308511,0822282111,広島県,広島市中区,基町10-52",
                "7538501,0839223111,山口県,山口市,滝町1-1",
                "7708570,0886212500,徳島県,徳島市,万代町1-1",
                "7608570,0878311111,香川県,高松市,番町4-1-10",
                "7908570,0899412111,愛媛県,松山市,一番町4-4-2",
                "7808570,0888231111,高知県,高知市,丸ノ内1-2-20",
                "8128577,0926511111,福岡県,福岡市博多区,東公園7-7",
                "8408570,0952242111,佐賀県,佐賀市,城内1-1-59",
                "8508570,0958241111,長崎県,長崎市,尾上町3-1",
                "8628570,0963831111,熊本県,熊本市中央区,水前寺6",
                "8708501,0975361111,大分県,大分市,大手町3-1-1",
                "8808501,0985267111,宮崎県,宮崎市,橘通東2-10-1",
                "8908577,0992862111,鹿児島県,鹿児島市,鴨池新町10-1",
                "9008570,0988662333,沖縄県,那覇市,泉崎1-2-2"
        };
        String apartmentBuilding = ",ヽヾゝゞ〃仝々〆ー表十能〜"; // chrome driver cannot handle 𠜎𠱓
        return getRandomValueFromArray(addressList, random)+apartmentBuilding;
    }

    public void setNamesRandomly() {
        String[] firstName = getNameRandomlyFromCsvFile("src/main/resources/investorMetaData/jp_first_name.csv", random);
        String[] lastName = getNameRandomlyFromCsvFile("src/main/resources/investorMetaData/jp_last_name.csv", random);
        setFamilyNameKana(lastName[0]);
        setFamilyName(lastName[1]);
        setGivenNameKana(firstName[0]);
        setGivenName(firstName[1]);
    }
}
