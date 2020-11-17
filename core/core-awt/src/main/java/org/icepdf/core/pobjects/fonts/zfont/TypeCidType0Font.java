package org.icepdf.core.pobjects.fonts.zfont;

import org.icepdf.core.util.Library;

import java.util.HashMap;

public class TypeCidType0Font extends CompositeFont {
    public TypeCidType0Font(Library library, HashMap entries) {
        super(library, entries);
    }

    @Override
    public void init() {
        super.init();
        parseCidToGidMap();
    }

    protected void parseCidToGidMap() {
        Object gidMap = library.getObject(entries, CID_TO_GID_MAP_KEY);
        System.out.println();
//        if (subtype.equals("CIDFontType0") && font instanceof ZFontOpenType && (isEmbedded || gidMap != null)) {
//            font = ((NFontOpenType) font).deriveFont(CMap.IDENTITY, toUnicodeCMap);
//        }
    }
}
