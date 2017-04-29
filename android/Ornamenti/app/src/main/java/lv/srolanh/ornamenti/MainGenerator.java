package lv.srolanh.ornamenti;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by srolanh on 16.6.4.
 */
class MainGenerator {

    private Context context;
    int imageID;
    private int level;
    private boolean imageChanged;
    ArrayList[] constants;
    static final int UNDEFINED_IMAGE_ID = -1;

    MainGenerator(Context ctx) {
        this(ctx, UNDEFINED_IMAGE_ID);
    }

    // sākuma konfigurācijas
    MainGenerator(Context ctx, int imageID) {
        context = ctx;
        this.imageID = imageID;
        imageChanged = false;
        constants = new ArrayList[8];
        ArrayList<ArrayList<Boolean>> KIEGELIS = new ArrayList<>(2);
        while (KIEGELIS.size() < 2) {
            KIEGELIS.add(new ArrayList<Boolean>());
        }
        KIEGELIS.get(0).addAll(Arrays.asList(false,true,true,false));
        KIEGELIS.get(1).addAll(Arrays.asList(false,true,true,false));
        constants[0] = KIEGELIS;
        ArrayList<ArrayList<Boolean>> UGUNSKRUSTS = new ArrayList<>(4);
        while (UGUNSKRUSTS.size() < 4) {
            UGUNSKRUSTS.add(new ArrayList<Boolean>());
        }
        UGUNSKRUSTS.get(0).addAll(Arrays.asList(false,false,true,false));
        UGUNSKRUSTS.get(1).addAll(Arrays.asList(true,true,true,false));
        UGUNSKRUSTS.get(2).addAll(Arrays.asList(false,true,true,true));
        UGUNSKRUSTS.get(3).addAll(Arrays.asList(false,true,false,false));
        constants[1] = UGUNSKRUSTS;
        ArrayList<ArrayList<Boolean>> M_UGUNSKRUSTS = new ArrayList<>(10);
        while (M_UGUNSKRUSTS.size() < 10) {
            M_UGUNSKRUSTS.add(new ArrayList<Boolean>());
        }
        M_UGUNSKRUSTS.get(0).addAll(Arrays.asList(false,false,false,false,false,true,false,false,false,false));
        M_UGUNSKRUSTS.get(1).addAll(Arrays.asList(false,false,false,false,true,true,false,false,false,false));
        M_UGUNSKRUSTS.get(2).addAll(Arrays.asList(false,false,true,true,true,false,false,true,false,false));
        M_UGUNSKRUSTS.get(3).addAll(Arrays.asList(false,false,false,true,true,false,true,true,false,false));
        M_UGUNSKRUSTS.get(4).addAll(Arrays.asList(true,true,false,false,true,true,true,true,true,false));
        M_UGUNSKRUSTS.get(5).addAll(Arrays.asList(false,true,true,true,true,true,false,false,true,true));
        M_UGUNSKRUSTS.get(6).addAll(Arrays.asList(false,false,true,true,false,true,true,false,false,false));
        M_UGUNSKRUSTS.get(7).addAll(Arrays.asList(false,false,true,false,false,true,true,true,false,false));
        M_UGUNSKRUSTS.get(8).addAll(Arrays.asList(false,false,false,false,true,true,false,false,false,false));
        M_UGUNSKRUSTS.get(9).addAll(Arrays.asList(false,false,false,false,true,false,false,false,false,false));
        constants[2] = M_UGUNSKRUSTS;
        ArrayList<ArrayList<Boolean>> L_UGUNSKRUSTS = new ArrayList<>(6);
        while (L_UGUNSKRUSTS.size() < 6) {
            L_UGUNSKRUSTS.add(new ArrayList<Boolean>());
        }
        L_UGUNSKRUSTS.get(0).addAll(Arrays.asList(false,false,false,false,true,false));
        L_UGUNSKRUSTS.get(1).addAll(Arrays.asList(true,true,false,true,true,false));
        L_UGUNSKRUSTS.get(2).addAll(Arrays.asList(false,true,true,true,false,false));
        L_UGUNSKRUSTS.get(3).addAll(Arrays.asList(false,false,true,true,true,false));
        L_UGUNSKRUSTS.get(4).addAll(Arrays.asList(false,true,true,false,true,true));
        L_UGUNSKRUSTS.get(5).addAll(Arrays.asList(false,true,false,false,false,false));
        constants[3] = L_UGUNSKRUSTS;
        ArrayList<ArrayList<Boolean>> KRUSTS = new ArrayList<>(4);
        while (KRUSTS.size() < 4) {
            KRUSTS.add(new ArrayList<Boolean>());
        }
        KRUSTS.get(0).addAll(Arrays.asList(false,true,true,false));
        KRUSTS.get(1).addAll(Arrays.asList(true,true,true,true));
        KRUSTS.get(2).addAll(Arrays.asList(true,true,true,true));
        KRUSTS.get(3).addAll(Arrays.asList(false,true,true,false));
        constants[4] = KRUSTS;
        ArrayList<ArrayList<Boolean>> ZALKTIS = new ArrayList<>(4);
        while (ZALKTIS.size() < 4) {
            ZALKTIS.add(new ArrayList<Boolean>());
        }
        ZALKTIS.get(0).addAll(Arrays.asList(false,true,true,false,false,false,false,false));
        ZALKTIS.get(1).addAll(Arrays.asList(true,false,false,true,false,false,true,false));
        ZALKTIS.get(2).addAll(Arrays.asList(false,true,false,false,true,false,false,true));
        ZALKTIS.get(3).addAll(Arrays.asList(false,false,false,false,false,true,true,false));
        constants[5] = ZALKTIS;
        ArrayList<ArrayList<Boolean>> ZALKTIS2 = new ArrayList<>(6);
        while (ZALKTIS2.size() < 6) {
            ZALKTIS2.add(new ArrayList<Boolean>());
        }
        ZALKTIS2.get(0).addAll(Arrays.asList(false,false,true,true,false,false,false,true,true,false,false,false));
        ZALKTIS2.get(1).addAll(Arrays.asList(false,true,true,true,true,false,true,true,true,true,false,false));
        ZALKTIS2.get(2).addAll(Arrays.asList(true,true,false,false,false,true,true,false,false,true,true,false));
        ZALKTIS2.get(3).addAll(Arrays.asList(false,true,true,false,false,true,true,false,false,false,true,true));
        ZALKTIS2.get(4).addAll(Arrays.asList(false,false,true,true,true,true,false,true,true,true,true,false));
        ZALKTIS2.get(5).addAll(Arrays.asList(false,false,false,true,true,false,false,false,true,true,false,false));
        constants[6] = ZALKTIS2;
        ArrayList<ArrayList<Boolean>> JUMIS_KOKS = new ArrayList<>(3);
        while (JUMIS_KOKS.size() < 3) {
            JUMIS_KOKS.add(new ArrayList<Boolean>());
        }
        JUMIS_KOKS.get(0).addAll(Arrays.asList(true,true,false,false,true,true));
        JUMIS_KOKS.get(1).addAll(Arrays.asList(false,true,true,true,true,false));
        JUMIS_KOKS.get(2).addAll(Arrays.asList(false,false,true,true,false,false));
        constants[7] = JUMIS_KOKS;
    }
    
    static void drawImage(Context ctx, Canvas canvas, ArrayList<ArrayList<Boolean>> image, int rSize) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        String colorPrimary = preferences.getString("pref_color1", "#0000FF");
        String colorSecondary = preferences.getString("pref_color0", "#FFFFFF");
        int xWidth = 0;
        int yHeight = 0;
        int widthPersistent = xWidth;
        int i;
        int j;
        Paint colorCurrent;
        Paint color0 = new Paint();
        Paint color1 = new Paint();
        color0.setStyle(Paint.Style.FILL);
        color1.setStyle(Paint.Style.FILL);
        color0.setColor(Color.parseColor(colorSecondary));
        color1.setColor(Color.parseColor(colorPrimary));
        for (i = 0; i < image.size(); i++) {
            for (j = 0; j < image.get(i).size(); j++) {
                if (image.get(i).get(j)) {
                    colorCurrent = color1;
                } else {
                    colorCurrent = color0;
                }
                canvas.drawRect(xWidth, yHeight, xWidth + rSize, yHeight + rSize, colorCurrent);
                xWidth += rSize;
            }
            xWidth = widthPersistent;
            yHeight += rSize;
        }
        //Log.i("Graphics", "Drew " + image.size() + " rows, " + image.get(0).size() + " columns of size " + rSize);
    }

    static void showOutOfMemoryErrorDialog(Context ctx) {

        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setTitle("Kļūda")
                .setMessage("\tPārāk liels ornaments\n\nOrnamenta lielums pārsniedz brīvo vietu atmiņā")
                .setPositiveButton("Labi", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {}
                });
        builder.create().show();
    }

    boolean getImageChanged() {
        return imageChanged;
    }

    void onImageDisplayed() {
        imageChanged = false;
    }

    int getLevel() {
        return level;
    }

    void setLevel(int level) {
        this.level = level;
    }

    private ArrayList genNet(int size, boolean inverse) {
        boolean color = !inverse; // norāda, vai tīklā jāliek 1 vai 0
        ArrayList<ArrayList<Boolean>> net = new ArrayList<>(); // tīkla sākums
        net.add(new ArrayList<Boolean>());
        net.add(new ArrayList<Boolean>());
        if (color) {
            net.get(0).add(true);
            net.get(1).add(false);
        } else {
            net.get(0).add(false);
            net.get(1).add(true);
        }
        while (net.get(0).size() < size) { // pirmā rinda
            if (!color) {
                net.get(0).addAll(Arrays.asList(true,true)); // liek tīklā 1
                color = true; // pārslēdzas uz otru krāsu
            } else {
                net.get(0).addAll(Arrays.asList(false,false)); // liek tīklā 0
                color = false; // pārslēdzas uz otru krāsu
            }
        }
        color = inverse; // otrās rindas sākums
        while (net.get(1).size() < size) {
            if (!color) {
                net.get(1).addAll(Arrays.asList(true,true));
                color = true;
            } else {
                net.get(1).addAll(Arrays.asList(false,false));
                color = false;
            }
        }
        if (net.get(0).size() > size) { // ja tīkls par 1 lielāks nekā vajag
            net.get(0).remove(net.get(0).size()-1); // noņemt elementu no pirmās rindas beigām
            net.get(1).remove(net.get(0).size()-1); // noņemt elementu no otrās rindas beigām
        }
        return net;
    }

    // ģenerē ornamentu
    ArrayList genFractal(ArrayList<ArrayList<Boolean>> image, boolean inverse,
                                       int level, boolean repeatMiddle, boolean repeatQuarter) {
        imageChanged = true;
        try {
            int sizeY = image.size();
            int sizeX = image.get(0).size();
            int middle = (int) Math.floor(sizeY) / 2; // norāda vidu, kas atkārtojams divreiz
            int quarter1 = (int) (Math.floor(sizeY / 4) - 1); // norāda pirmo ceturtdaļu
            int quarter2 = (int) (Math.ceil(sizeY * 0.75) + 2); // norāda trešo ceturtdaļu
            int rowLength = sizeX * 2; // norāda vajadzīgo rindas garumu šim līmenim
            int i;
            int j;
            for (i = sizeY - 1; i >= 0; i--) {
                if (image.get(i).size() == rowLength) {
                    continue;
                }
                for (j = sizeX - 1; j >= 0; j--) { // cikls pār katras rindas image[i] kartru elementu image[i][j]
                    image.get(i).add(j, image.get(i).get(j)); // atkārto katru elementu divreiz
                }
            }
            if (level > 1 && repeatMiddle) {
                image.add(middle + 1, image.get(middle)); // atkārto vidu divreiz
                sizeY++;
            }
            if (level > 2 && repeatQuarter) {
                image.add(quarter1 + 1, image.get(quarter1)); // atkārto pirmo ceturtdaļu
                image.add(quarter2 + 1, image.get(quarter2)); // atkārto trešo ceturtdaļu
                sizeY += 2;
            }
            ArrayList<ArrayList<Boolean>> net = genNet(image.get(0).size(), inverse); // ģenerē tīklu ornamentam
            int netIndex = 0; // norāda, kura tīkla rinda jāievieto
            for (i = 0; i <= sizeY; i++) {
                image.add(i, (ArrayList) net.get(netIndex).clone()); // ievieto vajadzīgo tīkla rindu
                i++;
                sizeY++;
                if (netIndex == 0) {
                    netIndex = 1;
                } else {
                    netIndex = 0;
                }
            }
            /*if (level == 1 || repeatMiddle) {
                image.add((ArrayList) net.get(1).clone());
            } else {
                image.add((ArrayList) net.get(0).clone());
            }*/
        } catch (OutOfMemoryError oom) {
            image = constants[imageID];
            setLevel(0);
            showOutOfMemoryErrorDialog(context);
        }
        return image;
    }
    
    public ArrayList genFractal(ArrayList<ArrayList<Boolean>> image, boolean inverse,
                                       int level, boolean repeatMiddle) {
        return genFractal(image, inverse, level, repeatMiddle, false);
    }

    public ArrayList genFractal(ArrayList<ArrayList<Boolean>> image, boolean inverse, int level) {
        return genFractal(image, inverse, level, true, false);
    }

}
