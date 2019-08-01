package cn.haozi.spring_security.fish.service.impl;

import cn.haozi.spring_security.admin.mapper.SysFileMapper;
import cn.haozi.spring_security.fish.entity.FishCategory;
import cn.haozi.spring_security.fish.entity.FishContent;
import cn.haozi.spring_security.fish.service.FishCategoryService;
import cn.haozi.spring_security.fish.service.FishContentService;
import cn.haozi.spring_security.fish.service.IShuiChanService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class ShuiChanServiceImpl implements IShuiChanService {

    private FishContentService fishContentService;

    private SysFileMapper fileMapper;

    private FishCategoryService fishCategoryService;

    private static  final String URL = "http://www.shuichan.cc/";
    /**
     * 根据链接获取文章内容
     * @param url
     * @return
     */
    @Override
    public boolean reptileArticle(String url,String title,int categoryId) {
        System.out.println("标题：-----------------"+title);
        String[] strTitle = title.split("\n");
        String[] t =  strTitle[0].split("：");
        StringBuilder sb =  new StringBuilder();
        if (strTitle.length >2) {
            String[] count =  strTitle[2].split("：");

            String[] c = count[1].split("");
            Arrays.stream(c).forEach(v->{
                if (v.matches("\\d+")) {
                    sb.append(String.valueOf(v));
                }
            });
            String[] time =  strTitle[3].split("：");
        }
        /**
         * 收集今日数据
         */
      /*  if (time.length>1){
            String now = DateUtil.today();;
            if (time[1].indexOf(now) == -1) {
                 return false;
            }
        }*/

        /**
         * 接下来就利用jsoup来解析前面取得的html
         */
        try {
            /**利用Jsoup类的静态方法，将html转换成一个Document对象*/
            Document document  = Jsoup.parse(new URL(url).openStream(), "GBK", url);
                /**获取文章*/
                Elements elements1 =  document.select("font#font_word");
                elements1.attr("style","font-size:16px");
                /**图片需要加上前缀*/
                Elements imgs = elements1.select("img");
                for (Element img:imgs) {
                    String src = img.attr("src");
                    img.attr("src","http://www.shuichan.cc/"+src);
                }
                String content = elements1.toString();
                String[] str =  content.split("&nbsp;&nbsp;&nbsp;&amp;nbsp");
                StringBuilder sb1 = new StringBuilder();
                for (int i = 0; i < str.length; i++) {
                    sb1.append("<span style='margin-left:10px'></span>");
                    sb1.append(str[i]);
                }
                FishContent fishContent = new FishContent();
                fishContent.setContent(sb1.toString());
                fishContent.setTitle(t[1]);
                fishContent.setCategoryId(categoryId);
                fishContent.setViewCount(Integer.parseInt(sb.toString()));

            //保存文章
            if (fishContent != null) {
                /** 文章是否相同*/
               List< FishContent> fishContent1 = fishContentService.findByTitle(fishContent.getTitle());
               if (fishContent1.size() == 0) {
                    fishContentService.save(fishContent);
               }
            }

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * 根据分类获取文章列表
     *  1、数据库中维护好分类以及链接
     */
    public void reptileCategory(String url) {
        List<FishCategory> list =   fishCategoryService.list(new QueryWrapper<FishCategory>().ne("parent_id","-1"));
        list.stream().forEach(v->{
            try {
                /**利用Jsoup类的静态方法，将html转换成一个Document对象*/
                Document document  = Jsoup.parse(new URL(v.getUrl()).openStream(), "GBK",v.getUrl());
                /**定位到获取资源的标签*/
                Elements element = document.select("table.tablew").prevAll();    //利用select选择器，取得需要的li元素集合
                Elements elements = element.select("a");    //取得a链接的集合
                for (int i =0 ;i  <elements.size();i++ ){
                    if (i >5) {
                        String title = elements.get(i).attr("title");
                        reptileArticle(URL+elements.get(i).attr("href"),title,v.getId());
                    }
                }
            } catch (IOException e) {
            }
        });

    }

    @Override
    public void reptilePrice(String url,Integer id) {


        try {
            /**利用Jsoup类的静态方法，将html转换成一个Document对象*/
            Document document  = Jsoup.parse(new URL(url).openStream(), "GBK",url);
            /**定位到获取资源的标签*/
            //利用Jsoup类的静态方法，将html转换成一个Document对象
            Elements element = document.select("table.tablew").eq(0);    //利用select选择器，取得需要的li元素集合
            Elements elements = element.select("a");    //取得a链接的集合
            for (int i =0 ;i  <elements.size();i++ ){
                if (i >5) {
                    String title = elements.get(i).attr("title");
                    String[] strTitle = title.split("\n");
                    String[] t =  strTitle[0].split("：");
                    StringBuilder sb =  new StringBuilder();
                    if (strTitle.length >2) {
                        String[] count =  strTitle[2].split("：");

                        String[] c = count[1].split("");
                        Arrays.stream(c).forEach(v->{
                            if (v.matches("\\d+")) {
                                sb.append(String.valueOf(v));
                            }
                        });
                        String[] time =  strTitle[3].split("：");
                    }

                    /**利用Jsoup类的静态方法，将html转换成一个Document对象*/
                    Document document2  = Jsoup.parse(new URL(URL+elements.get(i).attr("href")).openStream(), "GBK",URL+elements.get(i).attr("href"));
                    /**定位到获取资源的标签*/
                    //利用Jsoup类的静态方法，将html转换成一个Document对象
                    Elements elements1 =  document2.select("table.tf");
                    FishContent  fishContent = new FishContent();
                    fishContent.setContent(elements1.toString());
                    fishContent.setTitle(t[1]);
                    fishContent.setCategoryId(id);
                    fishContent.setViewCount(Integer.parseInt(sb.toString()));
                    /** 文章是否相同*/
                    List< FishContent> fishContent1 = fishContentService.findByTitle(fishContent.getTitle());
                    if (fishContent1.size() == 0) {
                        fishContentService.save(fishContent);
                    }
                }
            }
        } catch (IOException e) {
        }


    }

}
