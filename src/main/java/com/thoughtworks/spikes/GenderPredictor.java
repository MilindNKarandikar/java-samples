package com.thoughtworks.spikes;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;


public class GenderPredictor {


    public static final double THRESHOLD = 0.5;
    public static final int FEMALE_INDICATOR = 0;
    public static final int MALE_INDICATOR = 1;
    public static String coefficientsString ="['ua_Mozilla/5.0 (Linux; U; Android 4.1.2; en-; C1904 Build/15.1.C.2.8) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|0.290358528925:', "
            + "'ua_Mozilla/5.0 (Linux; U; Android 4.1.2; en-gb; GT-S7392 Build/JZO54K) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|0.244029637431:', 'ua_Mozilla/5.0 (Linux; U; Android 4.1.2; en-; GT-S7392 Build/JZO54K) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|0.178644203799:', 'ua_Mozilla/5.0 (Linux; U; Android 4.1.2; en-; GT-I9100 Build/JZO54K) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|0.892058533015:', 'appcat_,IAB17,IAB24,social_networking,sports|1.10400984611:', 'country_USA|-0.0754885657059:', 'ua_Mozilla/5.0 (iPhone; CPU iPhone OS 7_0_5 like Mac OS X) AppleWebKit/537.51.1 (KHTML, like Gecko) Mobile/11B601|0.524583898801:', 'ua_Mozilla/5.0 (Linux; U; Android 4.2.1; en-; Micromax A116 Build/JOP40D) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|0.870381634291:', 'appid_06bc4f70e3e44a149bb27d6fb6f473fa|0.376436590339:', 'appid_af8f72a9eaae43d6b1330cada08f86f6|-0.119962653827:', 'langauge_in|-0.291655975285:', 'appid_agltb3B1Yi1pbmNyDAsSA0FwcBiT__QVDA|0.49522312976:', 'appid_agltb3B1Yi1pbmNyDAsSA0FwcBj6kcoWDA|0.226985114672:', 'ua_Mozilla/5.0 (Linux; U; Android 4.1.2; en-gb; GT-P3100 Build/JZO54K) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Safari/534.30|0.294481140311:', 'appcat_,IAB24,IAB3,productivity,social_networking|0.000983163965594:', 'langauge_ar|0.912196904358:', 'ua_Mozilla/5.0 (Linux; U; Android 4.1.2; en-in; C1904 Build/15.1.C.2.8) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|0.379900967717:', 'ua_Mozilla/5.0 (Linux; U; Android 4.3; en-; Nexus 4 Build/JWR66Y) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|0.609427476433:', 'ua_Mozilla/5.0 (Linux; U; Android 4.3; en-gb; GT-I9505 Build/JSS15J) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|0.710519873357:', 'ua_Mozilla/5.0 (iPad; CPU OS 7_0_6 like Mac OS X) AppleWebKit/537.51.1 (KHTML, like Gecko) Mobile/11B651|0.438163505177:', 'appid_f7cc119ca9e1426c8d162d2d37c8558f|0.115058804706:', 'appid_203b3afe804311e295fa123138070049|-1.63608832214:', 'country_PHL|-0.739215754652:', 'ua_Mozilla/5.0 (Linux; U; Android 4.1.2; en-gb; GT-S5282 Build/JZO54K) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|-0.119046845198:', 'ua_Mozilla/5.0 (Linux; U; Android 4.2.2; en-us; GT-I9082 Build/JDQ39) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|0.694118808319:', 'ua_Mozilla/5.0 (Linux; U; Android 4.2.2; en-in; C2305 Build/16.0.B.0.21) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|0.326291900403:', 'ua_Mozilla/5.0 (Linux; U; Android 2.3.6; en-; GT-S6802 Build/GINGERBREAD) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1|0.157511916589:', 'ua_Mozilla/5.0 (Linux; U; Android 2.3.6; en-; GT-S5300 Build/GINGERBREAD) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1|-0.081506436498:', 'ua_Mozilla/5.0 (Linux; U; Android 2.3.6; en-gb; GT-I9070 Build/GINGERBREAD) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1|0.417805220669:', 'ua_Mozilla/5.0 (iPhone; CPU iPhone OS 7_0_3 like Mac OS X) AppleWebKit/537.51.1 (KHTML, like Gecko) Mobile/11B511|0.635495513666:', 'ua_Mozilla/5.0 (Linux; U; Android 4.1.2; en-gb; GT-S7262 Build/JZO54K) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|0.101418621468:', 'ua_Mozilla/5.0 (Linux; U; Android 4.2.2; en-; C2104 Build/15.3.A.0.26) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|0.535536099335:', 'ua_Mozilla/5.0 (Linux; U; Android 4.1.2; en-gb; GT-I9100 Build/JZO54K) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|0.757286307961:', 'ua_Mozilla/5.0 (Linux; U; Android 4.0.3; en-; HTC One V Build/IML74K) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|0.62428744468:', 'ua_Mozilla/5.0 (iPhone; CPU iPhone OS 6_1_4 like Mac OS X) AppleWebKit/536.26 (KHTML, like Gecko) Mobile/10B350|0.451103609723:', 'appid_agltb3B1Yi1pbmNyDAsSA0FwcBiXmaYVDA|0.434093249267:', 'ua_Mozilla/5.0 (Linux; U; Android 4.2.2; en-gb; GT-I9152 Build/JDQ39) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|0.521017757187:', 'country_AUS|-0.0562135082608:', 'appcat_,IAB14,IAB24,lifestyle,social_networking|0.957224389658:', 'appcat_,IAB20,IAB24,social_networking,travel|0.195058406788:', 'ua_Mozilla/5.0 (Linux; U; Android 4.3; en-gb; GT-N7100 Build/JSS15J) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|0.817042521006:', 'ua_Mozilla/5.0 (iPhone; CPU iPhone OS 7_0_4 like Mac OS X) AppleWebKit/537.51.1 (KHTML, like Gecko) Mobile/11B554a|0.735534371464:', 'ua_Mozilla/5.0 (iPad; CPU OS 7_1 like Mac OS X) AppleWebKit/537.51.2 (KHTML, like Gecko) Mobile/11D167|0.53827995696:', 'ua_Mozilla/5.0 (Linux; U; Android 4.2.2; en-gb; GT-I9500 Build/JDQ39) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|0.472694855999:', 'langauge_zh|-0.196694099405:', 'country_GBR|0.0444935978422:', 'appid_agltb3B1Yi1pbmNyDAsSA0FwcBiO2KISDA|0.249390549229:', 'ua_Mozilla/5.0 (Linux; U; Android 2.3.6; en-gb; GT-S5302 Build/GINGERBREAD) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1|0.18777997462:', 'ua_Mozilla/5.0 (Linux; U; Android 4.1.2; en-; ST26i Build/11.2.A.0.31) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|0.29936468311:', 'ua_Mozilla/5.0 (Linux; U; Android 4.3; en-gb; GT-I9500 Build/JSS15J) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|0.66375504008:', 'ua_Mozilla/5.0 (Linux; U; Android 2.3.6; en-gb; GT-S6802 Build/GINGERBREAD) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1|0.163421835343:', 'ua_Mozilla/5.0 (Linux; U; Android 4.1.2; en-; C5302 Build/12.0.A.2.254) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|0.81367431695:', 'ua_Mozilla/5.0 (Linux; U; Android 4.2.2; en-gb; GT-I9082 Build/JDQ39) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|0.458088357256:', 'appcat_,IAB24,IAB3,social_networking,utilities|-0.413529457123:', 'country_JP|0.650183057171:', 'appid_other|-0.566492339103:', 'ua_Mozilla/5.0 (iPhone; CPU iPhone OS 7_0_2 like Mac OS X) AppleWebKit/537.51.1 (KHTML, like Gecko) Mobile/11A501|0.608837882525:', 'ua_Mozilla/5.0 (iPad; CPU OS 7_0_3 like Mac OS X) AppleWebKit/537.51.1 (KHTML, like Gecko) Mobile/11B511|0.322647567535:', 'ua_Mozilla/5.0 (Linux; U; Android 4.0.3; en-; HTC Desire V Build/IML74K) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|0.472631622326:', 'ua_Mozilla/5.0 (Linux; U; Android 2.3.6; en-gb; GT-S6102 Build/GINGERBREAD) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1|0.310649716067:', 'ua_Mozilla/5.0 (Linux; U; Android 4.3; en-; GT-I9500 Build/JSS15J) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|0.742575462164:', 'ua_Mozilla/5.0 (Linux; U; Android 4.3; en-gb; GT-I9300 Build/JSS15J) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|0.408985242255:', 'ua_Mozilla/5.0 (Linux; U; Android 4.1.2; en-gb; SM-T211 Build/JZO54K) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Safari/534.30|0.341326833401:', 'ua_Mozilla/5.0 (Linux; U; Android 4.1.2; en-; GT-S6312 Build/JZO54K) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|-0.0484498932261:', 'ua_Mozilla/5.0 (Linux; U; Android 2.3.6; en-; GT-S5360 Build/GINGERBREAD) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1|0.047341892773:', 'ua_Mozilla/5.0 (Linux; U; Android 2.3.5; en-; HTC Explorer A310e Build/GRJ90) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1|0.342323694714:', 'country_NZL|0.194630966834:', 'ua_Mozilla/5.0 (iPhone; CPU iPhone OS 6_1_3 like Mac OS X) AppleWebKit/536.26 (KHTML, like Gecko) Mobile/10B329|0.595043548859:', 'ua_Mozilla/5.0 (Linux; U; Android 4.1.2; en-; Micromax A92 Build/JZO54K) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|0.400542078794:', 'ua_Mozilla/5.0 (Linux; U; Android 4.1.2; en-; GT-N7000 Build/JZO54K) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|1.10655820475:', 'appid_agltb3B1Yi1pbmNyDAsSA0FwcBihm_EVDA|-0.107675811354:', 'ua_Mozilla/5.0 (iPhone; CPU iPhone OS 7_1 like Mac OS X) AppleWebKit/537.51.2 (KHTML, like Gecko) Mobile/11D169|0.815321676131:', 'ua_Mozilla/5.0 (Linux; U; Android 4.2.1; en-; Micromax A210 Build/JOP40D) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|0.877383138737:', 'ua_Mozilla/5.0 (Linux; U; Android 4.1.2; en-; GT-I9070 Build/JZO54K) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|0.896987273438:', 'ua_Mozilla/5.0 (Linux; U; Android 4.1.2; en-; GT-S7262 Build/JZO54K) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|0.0129013893822:', 'ua_Mozilla/5.0 (Linux; U; Android 4.1.1; en-; Micromax A110 Build/JRO03C) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|0.593939476994:', 'appcat_,IAB24,social_networking|0.608441131011:', 'ua_Mozilla/5.0 (Linux; U; Android 4.1.2; en-gb; GT-N7000 Build/JZO54K) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|0.79360315368:', 'ua_Mozilla/5.0 (Linux; U; Android 4.1.2; en-gb; GT-I9300 Build/JZO54K) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|0.289890493457:', 'appid_b076640945104c77afb560e91588f4cf|1.73860686378:', 'ua_Mozilla/5.0 (iPhone; CPU iPhone OS 7_0 like Mac OS X) AppleWebKit/537.51.1 (KHTML, like Gecko) Mobile/11A465|0.526646199956:', 'ua_Mozilla/5.0 (Linux; U; Android 4.0.4; en-; A21 Build/IMM76I) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|0.685048815572:', 'ua_Mozilla/5.0 (Linux; U; Android 4.3; en-gb; SM-G7102 Build/JLS36C) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|0.442106451222:', 'ua_Mozilla/5.0 (iPad; CPU OS 7_0_4 like Mac OS X) AppleWebKit/537.51.1 (KHTML, like Gecko) Mobile/11B554a|0.482372248016:', 'appid_b82fb27091344db2912b607b3dc9162c|1.94799284809:', 'ua_Mozilla/5.0 (iPhone; CPU iPhone OS 7_0_6 like Mac OS X) AppleWebKit/537.51.1 (KHTML, like Gecko) Mobile/11B651|0.701606791173:', 'ua_Mozilla/5.0 (Linux; U; Android 4.2.2; en-; C6602 Build/10.3.1.A.2.67) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|0.505760241812:', 'appid_c01af50d4d1c425f8607032b39113009|0.209842159701:', 'ua_Mozilla/5.0 (Linux; U; Android 4.1.2; en-; GT-I8262 Build/JZO54K) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|0.148654350508:', 'ua_Mozilla/5.0 (Linux; U; Android 4.3; en-; SM-G7102 Build/JLS36C) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|0.694471738857:', 'ua_Mozilla/5.0 (Linux; U; Android 4.2.2; en-; GT-I9152 Build/JDQ39) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|0.534128057872:', 'country_MYS|0.128453096825:', 'ua_Mozilla/5.0 (Linux; U; Android 2.3.6; en-gb; GT-S5360 Build/GINGERBREAD) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1|0.0222636491621:', 'ua_Mozilla/5.0 (Linux; U; Android 4.1.2; en-; SM-T211 Build/JZO54K) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Safari/534.30|0.262094382168:', 'country_CA|0.316691978917:', 'appid_agltb3B1Yi1pbmNyDAsSA0FwcBjN668VDA|-0.0224492788896:', 'ua_Mozilla/5.0 (iPhone; CPU iPhone OS 7_1 like Mac OS X) AppleWebKit/537.51.2 (KHTML, like Gecko) Mobile/11D167|0.839112185263:', 'appid_dd1552f728504aec8aa8c219d35d169e|0.602372348124:', 'ua_Mozilla/5.0 (Linux; U; Android 4.1.2; en-; GT-I9300 Build/JZO54K) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|0.365770855219:', 'ua_Mozilla/5.0 (Linux; U; Android 2.3.6; en-; GT-S6102 Build/GINGERBREAD) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1|0.322379049748:', 'country_IND|0.979575220874:', 'ua_Mozilla/5.0 (Linux; U; Android 4.2.1; en-in; Micromax A116 Build/JOP40D) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|0.875508107024:', 'ua_Mozilla/5.0 (Linux; U; Android 4.1.2; en-us; GT-N7100 Build/JZO54K) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|0.393573850564:', 'country_PT|0.518647713601:', 'ua_Mozilla/5.0 (iPhone; CPU iPhone OS 6_1_2 like Mac OS X) AppleWebKit/536.26 (KHTML, like Gecko) Mobile/10B146|0.196593481349:', 'langauge_ms|-0.440521886853:', 'ua_Mozilla/5.0 (Linux; U; Android 4.2.2; en-; Micromax A76 Build/JDQ39) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|0.297660462596:', 'ua_Mozilla/5.0 (Linux; U; Android 4.0.4; en-us; Micromax A110 Build/IMM76D) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|0.483527299806:', 'appid_agltb3B1Yi1pbmNyDAsSA0FwcBiepNUSDA|1.65314428749:', 'ua_Mozilla/5.0 (Linux; U; Android 4.0.3; en-gb; GT-I9100 Build/IML74K) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|0.492241773689:', 'ua_Mozilla/5.0 (Linux; U; Android 4.1.2; en-gb; GT-N7100 Build/JZO54K) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|0.621430469252:', 'ua_Mozilla/5.0 (Linux; U; Android 4.2.2; en-us; Micromax A76 Build/JDQ39) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|0.46962002035:', 'ua_Mozilla/5.0 (Linux; U; Android 4.2.1; en-; Q700 Build/XOLOQ700) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|0.503091393588:', 'ua_Mozilla/5.0 (Linux; U; Android 4.2.2; en-; A500s Build/JDQ39) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|0.623322058938:', 'country_TWN|-0.166684642934:', 'ua_Mozilla/5.0 (Linux; U; Android 4.3; en-; SM-N900 Build/JSS15J) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|0.947069120026:', 'appid_75566986a60011e295fa123138070049|1.48688799174:', 'langauge_en|-0.45471281423:', 'ua_Mozilla/5.0 (Linux; U; Android 4.3; en-us; GT-N7100 Build/JSS15J) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|0.698657144706:', 'ua_Mozilla/5.0 (Linux; U; Android 4.1.2; en-; GT-I9082 Build/JZO54K) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|0.249148491194:', 'langauge_hi|0.523434484975:', 'appid_agltb3B1Yi1pbmNyDAsSA0FwcBiXh-0UDA|0.507171443111:', 'ua_Mozilla/5.0 (Linux; U; Android 4.0.4; en-; GT-S7562 Build/IMM76I) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|0.181154564554:', 'ua_Mozilla/5.0 (Linux; U; Android 4.1.2; en-; GT-I9100G Build/JZO54K) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|0.693790243423:', 'ua_Mozilla/5.0 (Linux; U; Android 2.3.6; en-gb; GT-S7500 Build/GINGERBREAD) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1|0.0741546332642:', 'country_HKG|-0.268034327621:', 'ua_Mozilla/5.0 (Linux; U; Android 4.3; en-; GT-N7100 Build/JSS15J) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|0.9707912314:', 'ua_Mozilla/5.0 (Linux; U; Android 4.1.2; en-in; Micromax A116 Build/JZO54K) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|0.579610370097:', 'ua_Mozilla/5.0 (Linux; U; Android 4.1.2; en-; GT-N7100 Build/JZO54K) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|0.745100047809:', 'ua_Mozilla/5.0 (Linux; U; Android 4.2.2; en-; C2305 Build/16.0.B.0.21) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|0.44678220347:', 'ua_Mozilla/5.0 (Linux; U; Android 4.0.3; en-; GT-I9100 Build/IML74K) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|0.271388372194:', 'ua_Mozilla/5.0 (Linux; U; Android 4.2.2; en-gb; GT-I9192 Build/JDQ39) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|0.788087897008:', 'country_SGP|0.049124754561:', 'country_GB|0.415592502785:', 'ua_Mozilla/5.0 (Linux; U; Android 4.2.1; en-in; Micromax A110Q Build/JOP40D) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|0.537579044161:', 'appid_agltb3B1Yi1pbmNyDAsSA0FwcBin6dgSDA|0.0784948752071:', 'ua_Mozilla/5.0 (Linux; U; Android 4.1.2; en-; A111 Build/JZO54K) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|0.26784050039:', 'ua_Mozilla/5.0 (Linux; U; Android 4.1.2; en-; GT-S5282 Build/JZO54K) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|-0.21022352871:', 'appcat_,IAB14,IAB7,healthcare_and_fitness,lifestyle|-1.69934655518:', 'ua_Mozilla/5.0 (Linux; U; Android 4.3; en-gb; SM-N9005 Build/JSS15J) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|0.553508403408:', 'ua_Mozilla/5.0 (Linux; U; Android 4.3; en-; GT-I9300 Build/JSS15J) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|0.347918940154:', 'ua_Mozilla/5.0 (Linux; U; Android 2.3.6; en-; GT-I9070 Build/GINGERBREAD) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1|-0.00534039848369:', 'ua_Mozilla/5.0 (Linux; U; Android 4.2.2; en-us; A500s Build/JDQ39) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|0.517106670665:', 'ua_Mozilla/5.0 (Linux; U; Android 4.2.2; en-; GT-S7582 Build/JDQ39) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|0.46974318704:', 'appcat_,IAB3,productivity,utilities|0.279551419229:', 'appid_4a5ef8f7f8bd459992feabce44663e7d|0.188481769882:', 'ua_Mozilla/5.0 (Linux; U; Android 4.1.2; en-us; Titanium S5 Build/JZO54K) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|0.634877939907:', 'ua_Mozilla/5.0 (Linux; U; Android 2.3.6; en-; GT-S5302 Build/GINGERBREAD) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1|0.0965840681793:', 'appcat_,IAB14,lifestyle|0.0302697713336:', 'ua_Mozilla/5.0 (Linux; U; Android 4.0.4; en-us; GT-S7562 Build/IMM76I) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|0.455064623165:', 'ua_Mozilla/5.0 (Linux; U; Android 4.1.2; en-; GT-P3100 Build/JZO54K) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Safari/534.30|0.452245880062:', 'appcat_,IAB1,IAB24,entertainment,social_networking|1.31090794859:', 'ua_Mozilla/5.0 (Linux; U; Android 4.2.2; en-; HTC One X Build/JDQ39) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|0.744652019179:', 'ua_Mozilla/5.0 (iPad; CPU OS 6_1_3 like Mac OS X) AppleWebKit/536.26 (KHTML, like Gecko) Mobile/10B329|0.352173982499:', 'appid_f011becb67fe4b92b2c0fac3c3a4e6e4|0.599608032104:', 'langauge_ko|0.328392816798:', 'ua_other|0.516508961707:', 'appid_97fa180c920e4194a5491a6c7480e6a6|0.422999202682:', 'ua_Mozilla/5.0 (Linux; U; Android 4.1.2; en-us; GT-I9300 Build/JZO54K) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|0.467482398753:', 'ua_Mozilla/5.0 (Linux; U; Android 4.1.2; en-; C2104 Build/15.0.A.2.17) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|0.104286162057:', 'appcat_,IAB24,IAB3,productivity,reference|0.0984069793853:', 'ua_Mozilla/5.0 (Linux; U; Android 4.1.2; en-gb; GT-I8262 Build/JZO54K) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|0.261124512624:', 'ua_Mozilla/5.0 (iPad; CPU OS 7_0_2 like Mac OS X) AppleWebKit/537.51.1 (KHTML, like Gecko) Mobile/11A501|0.419375938638:', 'ua_Mozilla/5.0 (Linux; U; Android 4.0.4; en-; Micromax A110 Build/IMM76D) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|0.573756892804:', 'country_US|0.38661052854:', 'ua_Mozilla/5.0 (Linux; U; Android 4.1.2; en-; XOLO_Q800 Build/JZO54K) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|0.617006173634:', 'ua_Mozilla/5.0 (Linux; U; Android 4.1.1; en-in; Micromax A110 Build/JRO03C) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|0.644028441911:', 'ua_Mozilla/5.0 (Linux; U; Android 4.1.2; en-gb; GT-I9082 Build/JZO54K) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|0.213145335542:', 'ua_Mozilla/5.0 (Linux; U; Android 4.1.1; en-; HTC Desire X Build/JRO03C) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|0.310612561541:', 'ua_Mozilla/5.0 (Linux; U; Android 2.3.6; en-gb; GT-S5830i Build/GINGERBREAD) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1|-0.0375866267536:', 'ua_Mozilla/5.0 (Linux; U; Android 4.3; en-gb; SM-N900 Build/JSS15J) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|0.886967502242:', 'ua_Mozilla/5.0 (Linux; U; Android 4.2.1; en-in; Micromax A210 Build/JOP40D) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|0.8075231447:', 'langauge_id|-0.21295082612:', 'ua_Mozilla/5.0 (Linux; U; Android 4.1.2; en-in; A111 Build/JZO54K) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|0.184364967202:', 'ua_Mozilla/5.0 (Linux; U; Android 4.0.4; en-gb; GT-S7562 Build/IMM76I) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|0.192784267772:', 'ua_Mozilla/5.0 (Linux; U; Android 4.2.2; en-gb; GT-S7582 Build/JDQ39) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|0.382875879746:', 'langauge_other|-0.3192891232:', 'ua_Mozilla/5.0 (Linux; U; Android 4.2.2; en-au; GT-I9505 Build/JDQ39) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|0.758084037413:', 'ua_Mozilla/5.0 (Linux; U; Android 4.1.2; en-; Micromax A116 Build/JZO54K) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|0.67196491999:', 'ua_Mozilla/5.0 (Linux; U; Android 2.3.6; en-; GT-S5830i Build/GINGERBREAD) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1|-0.105925986247:', 'appcat_|0.110283128462:', 'ua_Mozilla/5.0 (Linux; U; Android 4.2.2; en-; GT-I9082 Build/JDQ39) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|0.52049907815:', 'ua_Mozilla/5.0 (Linux; U; Android 4.1.2; en-gb; GT-I8552 Build/JZO54K) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|0.148978188883:', 'ua_Mozilla/5.0 (Linux; U; Android 4.0.3; en-; HTC Desire C Build/IML74K) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|0.26950256162:', 'appid_agltb3B1Yi1pbmNyDAsSA0FwcBiP2KISDA|0.164992590594:', 'ua_Mozilla/5.0 (Linux; U; Android 4.1.2; id-id; Andromax-c Build/JZO54K) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|0.348435892727:', 'ua_Mozilla/5.0 (Linux; U; Android 4.2.1; en-; Micromax A110Q Build/JOP40D) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|0.580951353004:', 'ua_Mozilla/5.0 (Linux; U; Android 4.2.2; en-; Micromax A74 Build/JDQ39) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|0.196440819:', 'ua_Mozilla/5.0 (Linux; U; Android 2.3.6; en-; GT-S7500 Build/GINGERBREAD) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1|0.231508924847:', 'ua_Mozilla/5.0 (Linux; U; Android 4.1.2; en-; Titanium S5 Build/JZO54K) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|0.672058027342:', 'appid_agltb3B1Yi1pbmNyDAsSA0FwcBizmcMUDA|0.600733173112:', 'ua_Mozilla/5.0 (Linux; U; Android 4.1.2; en-; GT-I8552 Build/JZO54K) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|0.104643107426:', 'appcat_other|0.986108826054:', 'ua_Mozilla/5.0 (Linux; U; Android 4.2.2; en-; GT-I9192 Build/JDQ39) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|0.673987750364:', 'country_IDN|-0.143108495272:', 'ua_Mozilla/5.0 (Linux; U; Android 4.0.4; en-; A89 Build/IMM76D) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|0.600405204098:', 'ua_Mozilla/5.0 (iPhone; CPU iPhone OS 6_0_1 like Mac OS X) AppleWebKit/536.26 (KHTML, like Gecko) Mobile/10A523|0.33189964556:', 'appcat_,IAB24,IAB9,IAB9-30,games,social_networking|-0.288966178698:', 'ua_Mozilla/5.0 (Linux; U; Android 4.2.2; en-; GT-I9500 Build/JDQ39) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|0.500098698404:', 'ua_Mozilla/5.0 (Linux; U; Android 4.1.2; en-gb; GT-N7105 Build/JZO54K) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30|0.682196725177:', 'ua_Mozilla/5.0 (iPhone; CPU iPhone OS 7_1_1 like Mac OS X) AppleWebKit/537.51.2 (KHTML, like Gecko) Mobile/11D201|0.880658620885:']";

    private static final HashSet<String> femaleGenderIndicators;
    private static final HashSet<String> maleGenderIndicators;

    static {
        femaleGenderIndicators = new HashSet<String>();
        femaleGenderIndicators.add("f");
        femaleGenderIndicators.add("F");
        femaleGenderIndicators.add("female");
        femaleGenderIndicators.add("FEMALE");

        maleGenderIndicators = new HashSet<String>();
        maleGenderIndicators.add("m");
        maleGenderIndicators.add("M");
        maleGenderIndicators.add("male");
        maleGenderIndicators.add("MALE");
    }

    private HashMap<String, Double> coefficientsMap;

    public GenderPredictor() {
        initializeCoefficients();
    }

    private void initializeCoefficients() {
        coefficientsMap = new HashMap<String, Double>();
        String formattedCoefficientsString = coefficientsString.trim().replace("]", "").replace("[","");
        String[] coefficients = formattedCoefficientsString.split("',");
        for (String coefficient : coefficients) {
            String keyValue = coefficient.trim().replace("'", "").replace(":", "");
            String[] keyValueTokens = keyValue.split("[|]");
            if (keyValueTokens.length == 2) {
                System.out.println(keyValueTokens[0]+":"+keyValueTokens[1]);
                coefficientsMap.put(keyValueTokens[0], Double.parseDouble(keyValueTokens[1]));
            } else {
                System.out.println("Invalid param: " + keyValue);
            }
        }
        System.out.println("Size of dictionary: " + coefficientsMap.size());
    }


    private double inferGender(String country, String appid, String appcat, String ua, String language, String gender) {

        if (gender != null) {
            if (femaleGenderIndicators.contains(gender)) {
                return FEMALE_INDICATOR;
            } else if (maleGenderIndicators.contains(gender)) {
                return MALE_INDICATOR;
            }
        }

        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("appcat_", appcat);
        parameters.put("langauge_", language);
        parameters.put("appid_", appid);
        parameters.put("country_", country);
        parameters.put("ua_", ua);

        return inferGender(parameters);
    }

    private double inferGender(Map<String, String> parameters) {

        double functionValue = 0.0f;
        try {

            for (String parameterType : parameters.keySet()) {
                functionValue += getCoefficient(parameterType, parameters.get(parameterType));
            }
            double inferredValue = (logisticRegressionFunction(functionValue) > THRESHOLD) ? MALE_INDICATOR : FEMALE_INDICATOR;

            return (int)inferredValue;
        }
        catch(Exception e) {return -1;}
    }

    private double logisticRegressionFunction(double functionValue) {
        return 1.0 / (1.0 + (Math.exp(-1 * functionValue)));
    }

    private double getCoefficient(String coefficientParamName, String coefficientType) {
        coefficientParamName = prefixKey(coefficientType, coefficientParamName);
        Double coefficient = coefficientsMap.get(coefficientParamName);
        if (coefficient == null) {
            String otherKey = coefficientType + "other";
            coefficient = coefficientsMap.containsKey(otherKey) ? coefficientsMap.get(otherKey) : 0.0;
        }
        return coefficient;
    }

    private String prefixKey(String prefix, String paramValue) {
        if(paramValue == null) {
            paramValue = "";
        }
        return prefix+paramValue;
    }

    public static void main(String[] args){
        GenderPredictor genderPredictor = new GenderPredictor();
        String country="PHL";
        String appid="203b3afe804311e295fa123138070049";
        String appcat=",IAB17,IAB24,social_networking,sports";
        String ua="Mozilla/5.0 (Linux; U; Android 4.1.2; en-; C1904 Build/15.1.C.2.8) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30";
        String language="";
        String gender="";

        genderPredictor.inferGender(country, appid, appcat, ua, language, gender);

    }
}
