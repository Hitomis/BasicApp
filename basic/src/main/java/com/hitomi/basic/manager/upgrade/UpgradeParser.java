package com.hitomi.basic.manager.upgrade;

import java.io.InputStream;

/**
 * Created by hitomi on 2016/12/14.
 */

public interface UpgradeParser {

    UpgradeInfo doParse(InputStream is);

}
