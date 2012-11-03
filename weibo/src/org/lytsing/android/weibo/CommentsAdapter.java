/*
 * Copyright (C) 2012 lytsing.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.lytsing.android.weibo;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.androidquery.AQuery;

import org.lytsing.android.weibo.model.Comment;
import org.lytsing.android.weibo.util.Util;

import java.util.ArrayList;

public class CommentsAdapter extends BaseAdapter {
    
    private ArrayList<Comment> mComments;
    
    private Context mContext;
    
    protected Handler mHandler;
    
    /**
     * 
     * @param context
     */
    public CommentsAdapter(Context context) {
        mComments = new ArrayList<Comment>();
        mHandler = new Handler();
        mContext = context;
    }

    /**
     * Get The Comment's count
     */
    @Override
    public int getCount() {
        return mComments.size();
    }

    @Override
    public Object getItem(int position) {
        return Integer.valueOf(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View commentsView;
        if (convertView != null && convertView.getId() == R.id.comment_list_item) {
            commentsView = convertView;
        } else {
            commentsView = Util.inflateView(R.layout.comment_list_item, mContext, null);
        }
        
        Comment comment = mComments.get(position);
        AQuery aq = new AQuery(commentsView);
        
        aq.id(R.id.author).text(comment.getAuthor());
        aq.id(R.id.time).text(comment.getCreationTime());
        
        TextView commentBody = (TextView) commentsView.findViewById(R.id.comment_body);
        commentBody.setText(comment.getBody());
        if (comment.hasTruncated()) {
            commentBody.setMaxLines(2);
            commentBody.setEllipsize(TextUtils.TruncateAt.END);
        } else {
            commentBody.setMaxLines(Integer.MAX_VALUE);
            commentBody.setEllipsize(null);
        }
        
        return commentsView;
    }
    
    public void addComment(Comment comment) {
        mComments.add(comment);
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
            }
        });
    }
}
