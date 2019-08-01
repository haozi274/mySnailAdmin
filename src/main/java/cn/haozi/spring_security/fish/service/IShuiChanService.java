package cn.haozi.spring_security.fish.service;

/**
 * 爬取中国养殖网数据接口
 *   不同的页面爬取数据的标签元素各不相同，需要定义不同的规则
 */
public interface IShuiChanService {
    /**
     * 根据分类爬取文章
     */
    boolean reptileArticle(String url, String title, int categoryId);

    void reptileCategory(String url);


    void reptilePrice(String url, Integer id);


}
