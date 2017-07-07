package com.maximbravo.bakeit;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class BakeItWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.bake_it_widget);
        Intent intent = new Intent(context, RecipesActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        views.setOnClickPendingIntent(R.id.ingredient_list, pendingIntent);
        views.setTextViewText(R.id.ingredient_list, BakingUtils.getWidgetText());
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent i) {
        if (i.getAction().equals("update_widget")) {
            // Manual or automatic widget update started

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.bake_it_widget);

            // Update text, images, whatever - here
            Intent intent = new Intent(context, RecipesActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            remoteViews.setOnClickPendingIntent(R.id.ingredient_list, pendingIntent);
            remoteViews.setTextViewText(R.id.ingredient_list, BakingUtils.getWidgetText());

            // Trigger widget layout update
            AppWidgetManager.getInstance(context).updateAppWidget(
                    new ComponentName(context, BakeItWidget.class), remoteViews);
        }
    }
}

