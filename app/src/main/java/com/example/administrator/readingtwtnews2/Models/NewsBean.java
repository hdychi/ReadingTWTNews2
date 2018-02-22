package com.example.administrator.readingtwtnews2.Models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/6/17.
 */

public class NewsBean {

    /**
     * error_code : -1
     * message :
     * data : [{"index":53662,"subject":"【图集】谈判与外交的艺术：沙祖康先生莅临天津大学","pic":"http://news.twt.edu.cn/public/news/wyynews/420_2017_06_06_21_25_31_cover_286.jpg","visitcount":357,"comments":0,"summary":"6月6日，前任联合国副秘书长沙祖康先生莅临天津大学。上午9时，沙祖康先生参观卫津路校..."},{"index":53657,"subject":"通信与技术的融合：邬贺铨做客北洋大讲堂","pic":"http://open.twtstudio.com/imgcache/633095635ae8534c018a69b591edb898.jpg","visitcount":118,"comments":0,"summary":"6月5日上午9时，由共青团天津大学委员会、电气自动化与信息工程学院、天津大学青年文化..."},{"index":53651,"subject":"玫瑰与海：北洋军乐团音乐沙龙","pic":"http://news.twt.edu.cn/public/news/wyynews/420_2017_05_29_14_25_02_cover_280.jpg","visitcount":1358,"comments":0,"summary":"5月28日晚7时，北洋军乐团二队沙龙音乐专场\u201c玫瑰与海\u201d在卫津路校区大学生活动中心一..."},{"index":53648,"subject":"十佳社团颁奖晚会：年度盛典 众彩纷呈","pic":"http://news.twt.edu.cn/public/news/wyynews/420_2017_05_27_18_16_20_cover_292.jpg","visitcount":1875,"comments":0,"summary":"5月25日晚上6时30分，天津大学社团庆典暨十佳社团标兵颁奖晚会于北洋园校区大通活动中心..."},{"index":53647,"subject":"她们 不只美而已","pic":"http://news.twt.edu.cn/public/news/wyynews/420_2017_05_27_00_16_04_cover_298.jpg","visitcount":1040,"comments":0,"summary":"你是否发现北洋园里存在着这样一群人？她们在你回眸处可见，在你抬眼处可寻。你是否留..."},{"index":53643,"subject":"知学讲坛：《奇葩说》正能量男神艾力做客天大","pic":"http://open.twtstudio.com/imgcache/8235c0a7d6ed8cfffa079388d142c0a8.jpg","visitcount":262,"comments":0,"summary":"5月24日下午3时20分，郑东图书馆南区一层多功能厅传出了阵阵笑声。新东方集团讲师、《超..."},{"index":53637,"subject":"\u201c强硬\u201d姑娘的逆袭之路：刘媛媛做客青年文化论坛","pic":"http://news.twt.edu.cn/public/news/wyynews/420_2017_05_24_02_32_29_cover_315.jpg","visitcount":1367,"comments":2,"summary":"\u201c向全世界热血喊话\u2014\u2014我不惧怕成为这样\u2019强硬\u2019的姑娘，不理会打量的目光和讥笑，也..."},{"index":53636,"subject":"五四表彰大会：不忘历史 开辟未来","pic":"http://news.twt.edu.cn/public/news/wyynews/420_2017_05_23_22_10_10_cover_315.jpg","visitcount":6112,"comments":0,"summary":"5月23日下午4时，天津大学纪念共青团成立95周年暨五四表彰大会于北洋园校区大通活动中心..."},{"index":53632,"subject":"天津大学时尚沙龙：眉眼之间 体味时尚","pic":"http://news.twt.edu.cn/public/news/wyynews/420_2017_05_22_02_14_03_cover_280.jpg","visitcount":1190,"comments":0,"summary":"5月21日下午3时，以\u201c走近时尚\u201d为题的天津大学时尚沙龙在卫津路校区西阶梯教室202举行..."},{"index":53631,"subject":"执子之手 邀君吟风赏越","pic":"http://news.twt.edu.cn/public/news/wyynews/420_2017_05_22_02_24_13_cover_280.jpg","visitcount":727,"comments":0,"summary":"\u201c江南灵秀出莺唱，啼笑喜怒成隽永。\u201d越剧作为中华文化瑰宝之一已悄然走过111年历程。..."},{"index":53627,"subject":"属于你的\u201c格林\u201d童话","pic":"http://open.twtstudio.com/imgcache/773ec002558e1d2a332bd120ed303f28.jpg","visitcount":234,"comments":0,"summary":"5月20日下午2时，《重返·狼群》天津大学超前点映与主创交流会于天南楼C4报告厅正式举行..."},{"index":53626,"subject":"【图集】甜蜜水果节 比心520","pic":"http://open.twtstudio.com/imgcache/463989b25398a04f69e6b8a340d16f85.jpg","visitcount":154,"comments":0,"summary":"5月20日早7时，在清晨温暖的阳光中，天津大学水果文化节在卫津路校区北洋广场拉开帷幕..."}]
     */

    private int error_code;
    private String message;
    private List<DataBean> data;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        /**
         * index : 53662
         * subject : 【图集】谈判与外交的艺术：沙祖康先生莅临天津大学
         * pic : http://news.twt.edu.cn/public/news/wyynews/420_2017_06_06_21_25_31_cover_286.jpg
         * visitcount : 357
         * comments : 0
         * summary : 6月6日，前任联合国副秘书长沙祖康先生莅临天津大学。上午9时，沙祖康先生参观卫津路校...
         */

        private int index;
        private String subject;
        private String pic;
        private int visitcount;
        private int comments;
        private String summary;

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public int getVisitcount() {
            return visitcount;
        }

        public void setVisitcount(int visitcount) {
            this.visitcount = visitcount;
        }

        public int getComments() {
            return comments;
        }

        public void setComments(int comments) {
            this.comments = comments;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }
        @Override
        public boolean equals(Object o){
            if(!(o instanceof NewsBean.DataBean)){
                return  false;
            }
            return ((DataBean) o).getSubject().equals(subject);
        }
    }
}
