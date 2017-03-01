/*
 * Copyright (c) Rakuten Card Co., Ltd. All Rights Reserved.
 * 
 * This program is the information assets which are handled
 * as "Strictly Confidential".
 * Permission of use is only admitted in Rakuten Card Co., Ltd.
 * If you don't have permission, MUST not be published,
 * broadcast, rewritten for broadcast or publication
 * or redistributed directly or indirectly in any medium.
 * 
 * $Id$
 */
package course.aggregater;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author suraj.mishra
 */
@ManagedBean(name = "dataBean")
@SessionScoped
public class dataBean {

    public String searchString;
    Map<String,List<String>> courses =new LinkedHashMap<String,List<String>>();
   
    

    

    public Map<String,List<String>> getCourses() {
        return this.courses;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }
  @PostConstruct
    public  void dataBean() {

          loadCoursesFromCodeCademy();
          loadCoursesFromUdacity();
//        loadCoursesFromCoursera();
//        loadCoursesFromAlison();
          System.out.println("course-size"+courses.size());
    }

    public void loadCoursesFromCodeCademy() {
        try {
            Document doc = Jsoup.connect("https://www.codecademy.com/learn/all").get();
            Elements element = doc.select("._25DB5jKl7vOKx0GzEg12NW");
            for (Element e : element) {
                List<String> courseDetails=new ArrayList<String>();
                Element link = e.select("a").first();
                String absHref = link.attr("abs:href");
                Elements el = e.select("._2w2jXRaHtlvTboJ8mIVanH");
                Document doc1=Jsoup.connect(absHref).get();
                Elements parentElement=doc1.select("._1XRkeRcO7hh1axYhmKiQMM");
                String overview=parentElement.select("._17BgxvIc-BHyTKH0aINNIt").text();
                courseDetails.add(el.select(".V30lAQsA1k6uhTEcKG0VL").text());
                courseDetails.add(overview);
                courses.put(absHref,courseDetails);
                
                System.out.println(overview);
                System.out.println(el.select(".V30lAQsA1k6uhTEcKG0VL").text());
                // System.out.println(doc1);
                System.out.println(absHref);
                System.out.println("---------------------------------------------");
                //System.out.println(courses);
            }
        } catch (IOException ex) {
            Logger.getLogger(dataBean.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    public void loadCoursesFromUdacity() {
        int i=0;
        try {
            Document doc = Jsoup.connect("https://www.udacity.com/courses/all").get();
            Elements element = doc.select(".course-summary-card");
            for (Element e : element) {
                List<String> courseDetails=new ArrayList<String>();
                Elements anchorClass = e.select(".h-slim");
                Elements anchor = anchorClass.select("a");
                //Document doc1=Jsoup.connect(anchor.attr("abs:href")).get();
                Elements parentElement=e.select(".col-sm-9");
                
                courseDetails.add(anchor.text());
                courseDetails.add(parentElement.text());
                courses.put(anchor.attr("abs:href"),courseDetails);
               // System.out.println(doc);
                System.out.println(anchor.text());
                System.out.println("--------------------------");
                System.out.println(anchor.text());
                System.out.println(anchor.attr("abs:href"));
            }
        } catch (IOException ex) {
            Logger.getLogger(dataBean.class.getName()).log(Level.SEVERE, null, ex);

        }

    }
/*
    public void loadCoursesFromCoursera() {
        try {
            Document doc = Jsoup.connect("https://www.coursera.org/browse/computer-science/software-development?languages=en").get();
            Elements element = doc.select(".rc-OfferingCard");

            for (Element e : element) {
                Elements div = e.select(".color-primary-text");
                Elements links = e.select("a");
                courses.put(links.attr("abs:href"),div.text());
                System.out.println(div.text());
                System.out.println(links.attr("abs:href"));

            }
        } catch (IOException ex) {
            Logger.getLogger(dataBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadCoursesFromAlison() {

        String[] StaticUrl = {"https://alison.com/learn/web-development", "https://alison.com/learn/python-programming", "https://alison.com/learn/programming",
            "https://alison.com/learn/c-programming", "https://alison.com/learn/databases", "https://alison.com/learn/games-development",
            "https://alison.com/learn/information-technology"};

        for (int i = 0; i < StaticUrl.length; i++) {
            try {
                Document doc = Jsoup.connect(StaticUrl[i]).get();
                Elements element = doc.select(".course-listing-box");

                for (Element e : element) {

                    Elements anchor = e.select("a");
                    courses.put(anchor.attr("abs:href"),anchor.text());
                    System.out.println(anchor.text());
                    System.out.println(anchor.attr("abs:href"));
                }

            } catch (IOException ex) {

                Logger.getLogger(dataBean.class.getName()).log(Level.SEVERE, null, ex);

            }

        }
    }
*/
}
