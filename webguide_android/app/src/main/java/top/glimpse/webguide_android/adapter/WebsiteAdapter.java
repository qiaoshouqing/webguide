package top.glimpse.webguide_android.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;

import top.glimpse.webguide_android.util.AsyncImageLoader;
import top.glimpse.webguide_android.R;
import top.glimpse.webguide_android.entity.WebsiteEntity;


/**
 * Created by joyce on 16-5-12.
 */
public class WebsiteAdapter extends BaseAdapter{

    private List<WebsiteEntity> listItems;
    private LayoutInflater inflater;
    private Activity activity;
    private AsyncImageLoader asyncImageLoader;

    public WebsiteAdapter(Activity activity, List<WebsiteEntity> listItems) {
        this.activity = activity;
        this.listItems = listItems;
        asyncImageLoader = new AsyncImageLoader(activity);
        inflater = activity.getLayoutInflater();
    }


    public void setListItems(List<WebsiteEntity> listItems) {
        this.listItems = listItems;
    }


    @Override
    public int getCount() {
        return listItems.size();
    }

    @Override
    public Object getItem(int position) {
        return listItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null)
        {
            convertView = inflater.inflate(R.layout.website_item, null);
            ImageView logo = (ImageView) convertView.findViewById(R.id.logo);
            TextView wname = (TextView) convertView.findViewById(R.id.wname);

            holder = new ViewHolder();
            holder.logo = logo;
            holder.wname = wname;

            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        WebsiteEntity pe = listItems.get(position);

        holder.logo.setImageResource(R.mipmap.ic_launcher);
        holder.logo.setTag(pe.getLogo());
        holder.wname.setText(pe.getWname());


        Bitmap photobitmap = asyncImageLoader.loadImage(holder.logo, pe.getLogo());
        if(photobitmap != null)
        {
            holder.logo.setImageBitmap(photobitmap);
        }

        return convertView;
    }

    private class ViewHolder {
        ImageView logo;
        TextView wname;
    }
}
