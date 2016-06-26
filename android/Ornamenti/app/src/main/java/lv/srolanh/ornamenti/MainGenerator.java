package lv.srolanh.ornamenti;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by srolanh on 16.6.4.
 */
public class MainGenerator {

    private static Context context;

    public static ArrayList[] init(Context ctx) {
        context = ctx;
        ArrayList[] constants = new ArrayList[6];
        final ArrayList<ArrayList<Integer>> KIEGELIS = new ArrayList<>(2);
        while (KIEGELIS.size() < 2) {
            KIEGELIS.add(new ArrayList<Integer>());
        }
        KIEGELIS.get(0).addAll(Arrays.asList(0,1,1,0));
        KIEGELIS.get(1).addAll(Arrays.asList(0,1,1,0));
        constants[0] = KIEGELIS;
        final ArrayList<ArrayList<Integer>> UGUNSKRUSTS = new ArrayList<>(4);
        while (UGUNSKRUSTS.size() < 4) {
            UGUNSKRUSTS.add(new ArrayList<Integer>());
        }
        UGUNSKRUSTS.get(0).addAll(Arrays.asList(0,0,1,0));
        UGUNSKRUSTS.get(1).addAll(Arrays.asList(1,1,1,0));
        UGUNSKRUSTS.get(2).addAll(Arrays.asList(0,1,1,1));
        UGUNSKRUSTS.get(3).addAll(Arrays.asList(0,1,0,0));
        constants[1] = UGUNSKRUSTS;
        final ArrayList<ArrayList<Integer>> M_UGUNSKRUSTS = new ArrayList<>(10);
        while (M_UGUNSKRUSTS.size() < 10) {
            M_UGUNSKRUSTS.add(new ArrayList<Integer>());
        }
        M_UGUNSKRUSTS.get(0).addAll(Arrays.asList(0,0,0,0,0,1,0,0,0,0));
        M_UGUNSKRUSTS.get(1).addAll(Arrays.asList(0,0,0,0,1,1,0,0,0,0));
        M_UGUNSKRUSTS.get(2).addAll(Arrays.asList(0,0,1,1,1,0,0,1,0,0));
        M_UGUNSKRUSTS.get(3).addAll(Arrays.asList(0,0,0,1,1,0,1,1,0,0));
        M_UGUNSKRUSTS.get(4).addAll(Arrays.asList(1,1,0,0,1,1,1,1,1,0));
        M_UGUNSKRUSTS.get(5).addAll(Arrays.asList(0,1,1,1,1,1,0,0,1,1));
        M_UGUNSKRUSTS.get(6).addAll(Arrays.asList(0,0,1,1,0,1,1,0,0,0));
        M_UGUNSKRUSTS.get(7).addAll(Arrays.asList(0,0,1,0,0,1,1,1,0,0));
        M_UGUNSKRUSTS.get(8).addAll(Arrays.asList(0,0,0,0,1,1,0,0,0,0));
        M_UGUNSKRUSTS.get(9).addAll(Arrays.asList(0,0,0,0,1,0,0,0,0,0));
        constants[2] = M_UGUNSKRUSTS;
        final ArrayList<ArrayList<Integer>> L_UGUNSKRUSTS = new ArrayList<>(6);
        while (L_UGUNSKRUSTS.size() < 6) {
            L_UGUNSKRUSTS.add(new ArrayList<Integer>());
        }
        L_UGUNSKRUSTS.get(0).addAll(Arrays.asList(0,0,0,0,1,0));
        L_UGUNSKRUSTS.get(1).addAll(Arrays.asList(1,1,0,1,1,0));
        L_UGUNSKRUSTS.get(2).addAll(Arrays.asList(0,1,1,1,0,0));
        L_UGUNSKRUSTS.get(3).addAll(Arrays.asList(0,0,1,1,1,0));
        L_UGUNSKRUSTS.get(4).addAll(Arrays.asList(0,1,1,0,1,1));
        L_UGUNSKRUSTS.get(5).addAll(Arrays.asList(0,1,0,0,0,0));
        constants[3] = L_UGUNSKRUSTS;
        final ArrayList<ArrayList<Integer>> KRUSTS = new ArrayList<>(4);
        while (KRUSTS.size() < 4) {
            KRUSTS.add(new ArrayList<Integer>());
        }
        KRUSTS.get(0).addAll(Arrays.asList(0,1,1,0));
        KRUSTS.get(1).addAll(Arrays.asList(1,1,1,1));
        KRUSTS.get(2).addAll(Arrays.asList(1,1,1,1));
        KRUSTS.get(3).addAll(Arrays.asList(0,1,1,0));
        constants[4] = KRUSTS;
        final ArrayList<ArrayList<Integer>> ZALKTIS = new ArrayList<>(4);
        while (ZALKTIS.size() < 4) {
            ZALKTIS.add(new ArrayList<Integer>());
        }
        ZALKTIS.get(0).addAll(Arrays.asList(0,1,1,0,0,0,0,0));
        ZALKTIS.get(1).addAll(Arrays.asList(1,0,0,1,0,0,1,0));
        ZALKTIS.get(2).addAll(Arrays.asList(0,1,0,0,1,0,0,1));
        ZALKTIS.get(3).addAll(Arrays.asList(0,0,0,0,0,1,1,0));
        constants[5] = ZALKTIS;
        return constants;
    }

    public static void drawImage(Context ctx, Canvas canvas, ArrayList<ArrayList<Integer>> image, int rSize) {
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
                if (image.get(i).get(j) == 1) {
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

    public static ArrayList genNet(int size, boolean inverse) {
        int color = inverse ? 0 : 1; // norāda, vai tīklā jāliek 1 vai 0
        ArrayList<ArrayList<Integer>> net = new ArrayList<ArrayList<Integer>>(); // tīkla sākums
        net.add(new ArrayList<Integer>());
        net.add(new ArrayList<Integer>());
        if (inverse) {
            net.get(0).add(1);
            net.get(1).add(0);
        } else {
            net.get(0).add(0);
            net.get(1).add(1);
        }
        while (net.get(0).size() < size) { // pirmā rinda
            if (color == 1) {
                net.get(0).addAll(Arrays.asList(1,1)); // liek tīklā 1
                color = 0; // pārslēdzas uz otru krāsu
            } else {
                net.get(0).addAll(Arrays.asList(0,0)); // liek tīklā 0
                color = 1; // pārslēdzas uz otru krāsu
            }
        }
        color = inverse ? 1 : 0; // otrās rindas sākums
        while (net.get(1).size() < size) {
            if (color == 1) {
                net.get(1).addAll(Arrays.asList(1, 1));
                color = 0;
            } else {
                net.get(1).addAll(Arrays.asList(0, 0));
                color = 1;
            }
        }
        if (net.get(0).size() > size) { // ja tīkls par 1 lielāks nekā vajag
            net.get(0).remove(net.get(0).size()-1); // noņemt elementu no pirmās rindas beigām
            net.get(1).remove(net.get(0).size()-1); // noņemt elementu no otrās rindas beigām
        }
        return net;
    }

    // ģenerē ornamentu
    public static ArrayList genFractal(ArrayList<ArrayList<Integer>> prevImage, boolean inverse,
                                       int level, boolean repeatMiddle, boolean repeatQuarter) {
        ArrayList<ArrayList<Integer>> image;
        try {
            image = (ArrayList) prevImage.clone(); // izveido rakstāmu kopiju
            int middle = (int) Math.floor(prevImage.size()) / 2; // norāda vidu, kas atkārtojams divreiz
            int quarter1 = (int) (Math.floor(prevImage.size() / 4) - 1); // norāda pirmo ceturtdaļu
            int quarter2 = (int) (Math.ceil(prevImage.size() * 0.75) + 2); // norāda trešo ceturtdaļu
            int rowLength = prevImage.get(0).size() * 2; // norāda vajadzīgo rindas garumu šim līmenim
            int i;
            int j;
            for (i = prevImage.size() - 1; i >= 0; i--) {
                if (image.get(i).size() == rowLength) {
                    continue;
                }
                for (j = prevImage.get(i).size() - 1; j >= 0; j--) { // cikls pār katras rindas image[i] kartru elementu image[i][j]
                    image.get(i).add(j, prevImage.get(i).get(j)); // atkārto katru elementu divreiz
                }
            }
            if (level > 1 && repeatMiddle) {
                image.add(middle + 1, image.get(middle)); // atkārto vidu divreiz
            }
            if (level > 2 && repeatQuarter) {
                image.add(quarter1 + 1, image.get(quarter1)); // atkārto pirmo ceturtdaļu
                image.add(quarter2 + 1, image.get(quarter2)); // atkārto trešo ceturtdaļu
            }
            ArrayList<ArrayList<Integer>> net = genNet(image.get(1).size(), inverse); // ģenerē tīklu ornamentam
            int netIndex = 0; // norāda, kura tīkla rinda jāievieto
            for (i = image.size() - 1; i >= 0; i--) {
                image.add(i, (ArrayList) net.get(netIndex).clone()); // ievieto vajadzīgo tīkla rindu
                if (netIndex == 0) {
                    netIndex = 1;
                } else {
                    netIndex = 0;
                }
            }
            if (level == 1 || repeatMiddle) {
                image.add((ArrayList) net.get(netIndex == 1 ? 0 : 1).clone());
            } else {
                image.add((ArrayList) net.get(netIndex).clone());
            }
        } catch (OutOfMemoryError oom) {
            image = prevImage;
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Kļūda")
                    .setMessage("\tPārāk liels ornaments\n\nOrnamenta lielums pārsniedz brīvo vietu atmiņā")
                    .setPositiveButton("Labi", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {}
                    });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        return image;
    }

    // definēt funkciju bez repeatQuarter argumenta
    public static ArrayList genFractal(ArrayList<ArrayList<Integer>> prevImage, boolean inverse,
                                       int level, boolean repeatMiddle) {
        return genFractal(prevImage, inverse, level, repeatMiddle, false);
    }

    // definēt funkciju bez repeatMiddle, repeatQuarter argumentiem
    public static ArrayList genFractal(ArrayList<ArrayList<Integer>> prevImage, boolean inverse, int level) {
        return genFractal(prevImage, inverse, level, true, false);
    }

}
