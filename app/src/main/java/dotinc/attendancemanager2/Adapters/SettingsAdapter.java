package dotinc.attendancemanager2.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import dotinc.attendancemanager2.R;
import dotinc.attendancemanager2.Utils.Helper;

/**
 * Created by ddvlslyr on 12/2/16.
 */
public class SettingsAdapter extends ArrayAdapter<String> {
    ArrayList<String> options;
    ArrayList<Integer> icons;
    Context context;

    public SettingsAdapter(Context context, ArrayList<String> options, ArrayList<Integer> icons) {
        super(context, R.layout.settings_single_row, R.id.options, options);

        this.options = options;
        this.context = context;
        this.icons = icons;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.settings_single_row, parent, false);
        }

        TextView name = (TextView) convertView.findViewById(R.id.options);
        //name.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/josefin_sans_regular.ttf"));
        ImageView icon = (ImageView) convertView.findViewById(R.id.sett_icon);

        name.setText("" + options.get(position));
        name.setTypeface(Typeface.createFromAsset(context.getAssets(), Helper.OXYGEN_REGULAR));
        icon.setImageResource(icons.get(position));
        return convertView;
    }
}
