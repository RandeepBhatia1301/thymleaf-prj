package org.ril.hrss.utility;

public class PaginationUtility {
    public String createLink(Integer count, Integer limit, String url, Integer currentPage) {

        url = url.concat("?page=");
     /*   Integer previousPageNum = currentPage - 1;
        Integer nextPageNum = currentPage + 1;
*/

        Integer previousPageNum = currentPage - 1;
        Integer nextPageNum = currentPage + 1;
        /*String wraper = "<ul class='pagination'></ul>";*/
        String wraper = "<ul class='pagination'>";
        String wraperEnd = "</ul>";

        String previous = previousPageNum <= 0 ? "<li ><a href='#'>Previous</a></li>" : "<li ><a href='" + url + previousPageNum + "'>Previous</a></li>";
        String next = nextPageNum > currentPage ? "<li ><a href='#'>Next</a></li>" : "<li><a href='" + url + nextPageNum + "'>Next</a></li>";

        Double pages = Math.ceil((double) count / limit);

        String links = "<li class='%s'><a href='%s'>%s</a></li>";
        Integer iter;
        String respLink = "";
        respLink = respLink.concat(wraper) + "\n";
        respLink = respLink.concat(previous) + "\n";
        for (iter = 2; iter <= pages; iter++) {
            String activeClass = iter.equals(currentPage) ? "active" : "none";
            respLink = respLink.concat(String.format(links, activeClass, url + iter, iter) + "\n");
        }
        respLink = respLink.concat(next) + "\n";
        respLink = respLink.concat(wraperEnd);
        return respLink;
    }
}
