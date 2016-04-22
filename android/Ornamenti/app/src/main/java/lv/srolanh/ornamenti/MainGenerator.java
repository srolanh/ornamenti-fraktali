package lv.srolanh.ornamenti;

import android.content.Context;
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
public class MainGenerator {

    public static ArrayList[] init() {
        ArrayList[] constants = new ArrayList[5];
        final ArrayList<ArrayList<Integer>> KIEGELIS_1 = new ArrayList<>();
        KIEGELIS_1.add(new ArrayList<Integer>());
        KIEGELIS_1.add(new ArrayList<Integer>());
        KIEGELIS_1.get(0).addAll(Arrays.asList(0,1,1,0));
        KIEGELIS_1.get(1).addAll(Arrays.asList(0,1,1,0));
        constants[0] = KIEGELIS_1;
        final ArrayList<ArrayList<Integer>> UGUNSKRUSTS_1 = new ArrayList<>();
        UGUNSKRUSTS_1.addAll(Arrays.asList(new ArrayList<Integer>(), new ArrayList<Integer>(),
                new ArrayList<Integer>(), new ArrayList<Integer>()));
        UGUNSKRUSTS_1.get(0).addAll(Arrays.asList(0,0,1,0));
        UGUNSKRUSTS_1.get(1).addAll(Arrays.asList(1,1,1,0));
        UGUNSKRUSTS_1.get(2).addAll(Arrays.asList(0,1,1,1));
        UGUNSKRUSTS_1.get(3).addAll(Arrays.asList(0,1,0,0));
        constants[1] = UGUNSKRUSTS_1;
        final ArrayList<ArrayList<Integer>> M_UGUNSKRUSTS_1 = new ArrayList<>();
        M_UGUNSKRUSTS_1.addAll(Arrays.asList(new ArrayList<Integer>(), new ArrayList<Integer>(),
                new ArrayList<Integer>(), new ArrayList<Integer>(), new ArrayList<Integer>(),
                new ArrayList<Integer>(), new ArrayList<Integer>(), new ArrayList<Integer>(),
                new ArrayList<Integer>(), new ArrayList<Integer>()));
        M_UGUNSKRUSTS_1.get(0).addAll(Arrays.asList(0,0,0,0,0,1,0,0,0,0));
        M_UGUNSKRUSTS_1.get(1).addAll(Arrays.asList(0,0,0,0,1,1,0,0,0,0));
        M_UGUNSKRUSTS_1.get(2).addAll(Arrays.asList(0,0,1,1,1,0,0,1,0,0));
        M_UGUNSKRUSTS_1.get(3).addAll(Arrays.asList(0,0,0,1,1,0,1,1,0,0));
        M_UGUNSKRUSTS_1.get(4).addAll(Arrays.asList(1,1,0,0,1,1,1,1,1,0));
        M_UGUNSKRUSTS_1.get(5).addAll(Arrays.asList(0,1,1,1,1,1,0,0,1,1));
        M_UGUNSKRUSTS_1.get(6).addAll(Arrays.asList(0,0,1,1,0,1,1,0,0,0));
        M_UGUNSKRUSTS_1.get(7).addAll(Arrays.asList(0,0,1,0,0,1,1,1,0,0));
        M_UGUNSKRUSTS_1.get(8).addAll(Arrays.asList(0,0,0,0,1,1,0,0,0,0));
        M_UGUNSKRUSTS_1.get(9).addAll(Arrays.asList(0,0,0,0,1,0,0,0,0,0));
        constants[2] = M_UGUNSKRUSTS_1;
        final ArrayList<ArrayList<Integer>> L_UGUNSKRUSTS_1 = new ArrayList<>();
        L_UGUNSKRUSTS_1.addAll(Arrays.asList(new ArrayList<Integer>(), new ArrayList<Integer>(),
                new ArrayList<Integer>(), new ArrayList<Integer>(), new ArrayList<Integer>(),
                new ArrayList<Integer>()));
        L_UGUNSKRUSTS_1.get(0).addAll(Arrays.asList(0,0,0,0,1,0));
        L_UGUNSKRUSTS_1.get(1).addAll(Arrays.asList(1,1,0,1,1,0));
        L_UGUNSKRUSTS_1.get(2).addAll(Arrays.asList(0,1,1,1,0,0));
        L_UGUNSKRUSTS_1.get(3).addAll(Arrays.asList(0,0,1,1,1,0));
        L_UGUNSKRUSTS_1.get(4).addAll(Arrays.asList(0,1,1,0,1,1));
        L_UGUNSKRUSTS_1.get(5).addAll(Arrays.asList(0,1,0,0,0,0));
        constants[3] = L_UGUNSKRUSTS_1;
        final ArrayList<ArrayList<Integer>> KRUSTS_1 = new ArrayList<>();
        KRUSTS_1.addAll(Arrays.asList(new ArrayList<Integer>(), new ArrayList<Integer>(),
                new ArrayList<Integer>(), new ArrayList<Integer>()));
        KRUSTS_1.get(0).addAll(Arrays.asList(0,1,1,0));
        KRUSTS_1.get(1).addAll(Arrays.asList(1,1,1,1));
        KRUSTS_1.get(2).addAll(Arrays.asList(1,1,1,1));
        KRUSTS_1.get(3).addAll(Arrays.asList(0,1,1,0));
        constants[4] = KRUSTS_1;
        return constants;
    }

    public static void drawImage(Context ctx, Canvas canvas, ArrayList<ArrayList<Integer>> image) {
        int[] dimens = MainActivity.dimensions;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        String colorPrimary = preferences.getString("pref_color1", "#0000FF");
        String colorSecondary = preferences.getString("pref_color0", "#FFFFFF");
        int rSize = dimens[0] / image.get(0).size();
        if (rSize == 0) {
            rSize = 1;
        }
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
        Log.i("Graphics", "Drew " + image.size() + " rows, " + image.get(0).size() + " columns of size " + rSize);
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
    public static ArrayList genFractal(ArrayList<ArrayList<Integer>> prevImage, boolean inverse, int level, Boolean repeatMiddle) {
        if (repeatMiddle == null) {
            repeatMiddle = true;
        }
        ArrayList<ArrayList<Integer>> image = (ArrayList) prevImage.clone(); // izveido rakstāmu kopiju
        int middle = (int) Math.floor(prevImage.size() / 2); // norāda vidu, kas atkārtojams divreiz
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
        if (repeatMiddle) {
            image.add((ArrayList) net.get(netIndex == 1 ? 0 : 1).clone());
        } else {
            image.add((ArrayList) net.get(netIndex).clone());
        }
        return image;
    }

// māras ugunskrusts
/*i = [
[0,0,0,0,0,1,0,0,0,0],
[0,0,0,0,1,1,0,0,0,0],
[0,0,1,1,1,0,0,1,0,0],
[0,0,0,1,1,0,1,1,0,0],
[1,1,0,0,1,1,1,1,1,0],
[0,1,1,1,1,1,0,0,1,1],
[0,0,1,1,0,1,1,0,0,0],
[0,0,1,0,0,1,1,1,0,0],
[0,0,0,0,1,1,0,0,0,0],
[0,0,0,0,1,0,0,0,0,0]
];*/

// ugunskrusts
// i = [[0,0,1,0],[1,1,1,0],[0,1,1,1],[0,1,0,0]]

// ķieģelis
// i = [[0,1,1,0],[0,1,1,0]];

}
