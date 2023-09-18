package ecom.udpm.vn.service.impl;

import ecom.udpm.vn.dto.model.Metadata;
import ecom.udpm.vn.dto.response.revenue.ListRevenueResponse;
import ecom.udpm.vn.dto.response.revenue.RevenueFilterRequest;
import ecom.udpm.vn.dto.response.revenue.RevenueResponse;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class RevenueService {
    private final EntityManager em;


    public List<RevenueResponse> getListRevenue(RevenueFilterRequest filter) {
        String queryFilter = "";
        String queryFilterLastPeriod = "";

        if (filter.getStartTime() != null && filter.getEndTime() != null) {
            queryFilter = "select DATE_FORMAT(orders.created_time, :timeType) as date,\n" +
                    "       round(ifnull(sum(orders.total_price),0) - ifnull(sum(money_return), 0)  +\n" +
                    "             ifnull(sum(orders.fee_money), 0))                 as netRevenure\n" +
                    "from order_purchase orders\n" +
                    "         inner join order_by_status_history orderHis on orders.id = orderHis.order_purchase_id\n" +
                    "where orderHis.status_id in (8,16)\n" +
                    "  and orders.created_time >= :timeCreated\n" +
                    "  and orders.created_time <= :timeCreatedTo\n" +
                    "group by date\n" +
                    "order by date asc;".toString();
            queryFilterLastPeriod = "select DATE_FORMAT(orders.created_time, :timeType) as date,\n" +
                    "       round(ifnull(sum(orders.total_price),0) - ifnull(sum(money_return), 0)  +\n" +
                    "             ifnull(sum(orders.fee_money), 0))                 as netRevenure\n" +
                    "from order_purchase orders\n" +
                    "         inner join order_by_status_history orderHis on orders.id = orderHis.order_purchase_id\n" +
                    "where orderHis.status_id in (8,16)\n" +
                    "  and orders.created_time >= :timeCreated\n" +
                    "  and orders.created_time <= :timeCreatedTo\n" +
                    "group by date\n" +
                    "order by date asc;".toString();
        } else {
            queryFilter = "select DATE_FORMAT(orders.created_time, :timeType) as date,\n" +
                    "       round(sum(orders.total_price) - ifnull(sum(money_return), 0)  +\n" +
                    "             ifnull(sum(orders.fee_money), 0))                 as netRevenure\n" +
                    "from order_purchase orders\n" +
                    "         inner join order_by_status_history orderHis on orders.id = orderHis.order_purchase_id\n" +
                    "where orderHis.status_id in (8,16)\n" +
                    "group by date\n" +
                    "order by date asc;;".toString();
            queryFilterLastPeriod = "select DATE_FORMAT(orders.created_time, :timeType) as date,\n" +
                    "       round(sum(orders.total_price) - ifnull(sum(money_return), 0)  +\n" +
                    "             ifnull(sum(orders.fee_money), 0))                 as netRevenure\n" +
                    "from order_purchase orders\n" +
                    "         inner join order_by_status_history orderHis on orders.id = orderHis.order_purchase_id\n" +
                    "where orderHis.status_id in (8,16)\n" +
                    "group by date\n" +
                    "order by date asc;".toString();
        }
        Query queryNativeFilter = em.createNativeQuery(queryFilter);
        Query queryNativeFilterLastPeriod = em.createNativeQuery(queryFilterLastPeriod);
        switch (filter.getTimeType()) {
            case "day":
                queryNativeFilter.setParameter("timeType", "'%Y-%m-%d'");
                queryNativeFilterLastPeriod.setParameter("timeType", "'%Y-%m-%d'");
                break;
            case "month":
                queryNativeFilter.setParameter("timeType", "'%Y-%m'");
                queryNativeFilterLastPeriod.setParameter("timeType", "'%Y-%m'");
                break;
        }

        if (filter.getStartTime() != null) {
            var diffDays = Period.between(filter.getStartTime(),filter.getEndTime());
            var lastPeriodTime = filter.getStartTime().minus(diffDays);
            queryNativeFilter.setParameter("timeCreated", filter.getStartTime().atStartOfDay(ZoneId.systemDefault()).toInstant());
            queryNativeFilterLastPeriod.setParameter("timeCreated", lastPeriodTime.atStartOfDay(ZoneId.systemDefault()).toInstant());
        }
        if (filter.getEndTime() != null) {
            queryNativeFilter.setParameter("timeCreatedTo", filter.getEndTime().atStartOfDay(ZoneId.systemDefault()).plusDays(1).minusSeconds(1).toInstant());
            queryNativeFilterLastPeriod.setParameter("timeCreatedTo", filter.getStartTime().atStartOfDay(ZoneId.systemDefault()).toInstant());
        }
        List<RevenueResponse> responses = new ArrayList<>();

        List<Object[]> objects = queryNativeFilter.getResultList();
        List<Object[]> objectsLastPeriod = queryNativeFilterLastPeriod.getResultList();
        for (Object[] object : objects) {
            String date = String.valueOf(object[0]).replace("'", "");
            String valueCompare= "";
            if (filter.getStartTime() != null && filter.getEndTime() != null){
                if (!Objects.equals(filter.getTimeType(), "day")){
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
                    YearMonth localDate = YearMonth.parse(date, formatter);
                    valueCompare = localDate.minusMonths(Period.between(filter.getStartTime(),filter.getEndTime()).getMonths()).format(DateTimeFormatter.ofPattern("yyyy-MM"));
                }else{
                    DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate localDate = LocalDate.parse(date,df);
                    valueCompare = localDate.minus(Period.between(filter.getStartTime(),filter.getEndTime())).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                }
            }
            RevenueResponse revenueResponse = new RevenueResponse();
            revenueResponse.setDate(date);
            revenueResponse.setNetRevenue(BigDecimal.valueOf(Long.parseLong(String.valueOf(Math.round(Float.parseFloat(object[1].toString()))))));
            for (Object[] objects1 : objectsLastPeriod){
                String dateLastPeriod = String.valueOf(objects1[0]).replace("'", "");
                if (valueCompare.equals(dateLastPeriod)){
                    revenueResponse.setLastPeriodDate(dateLastPeriod);
                    revenueResponse.setLastPeriodNetRevenue(BigDecimal.valueOf(Long.parseLong(objects1[1].toString())));
                }
            }
            responses.add(revenueResponse);
        }
        return responses;
    }

    private ListRevenueResponse filterRevenue(RevenueFilterRequest filter) {
        String queryFilter = buildNativeQueryFilter(filter);
        Query queryNativeFilter = em.createNativeQuery(queryFilter);
        String queryCount = buildNativeQueryCount(filter);
        Query queryNativeCount = em.createNativeQuery(queryCount);

        if (filter.getTimeType() != null) {
            switch (filter.getTimeType()) {
                case "day":
                    String paramDay = "'%Y/%m/%d'";
                    queryNativeFilter.setParameter("timeType", paramDay);
                    queryNativeCount.setParameter("timeType", paramDay);
                    break;
                case "month":
                    String paramMonth = "'%Y/%m'";
                    queryNativeFilter.setParameter("timeType", paramMonth);
                    queryNativeCount.setParameter("timeType", paramMonth);
                    break;
            }
        }

        if (filter.getStartTime() != null || filter.getEndTime() != null) {
            if (filter.getStartTime() != null) {
                queryNativeFilter.setParameter("timeCreated", filter.getStartTime().atStartOfDay(ZoneId.systemDefault()).toInstant());
                queryNativeCount.setParameter("timeCreated", filter.getStartTime().atStartOfDay(ZoneId.systemDefault()).toInstant());
            }
            if (filter.getEndTime() != null) {
                queryNativeFilter.setParameter("timeCreatedTo", filter.getEndTime().atStartOfDay(ZoneId.systemDefault()).plusDays(1).minusSeconds(1).toInstant());
                queryNativeCount.setParameter("timeCreatedTo", filter.getEndTime().atStartOfDay(ZoneId.systemDefault()).plusDays(1).minusSeconds(1).toInstant());
            }
        }
        queryNativeFilter.setParameter("limit", filter.getLimit());
        Integer offset = (filter.getPage() - 1) * filter.getLimit();
        queryNativeFilter.setParameter("offset", offset);
        List<RevenueResponse> responses = new ArrayList<>();
        List<Object[]> objects = queryNativeFilter.getResultList();
        for (Object[] object : objects) {
            RevenueResponse revenueResponse = new RevenueResponse();
            revenueResponse.setDate(String.valueOf(object[0]).replace("'", ""));
            revenueResponse.setTotalOrderAmount(BigDecimal.valueOf(Long.parseLong(String.valueOf(Math.round(Float.parseFloat(object[1].toString()))))));
            revenueResponse.setOrderCount(Integer.parseInt(object[2].toString()));
            revenueResponse.setReturnOrderAmount(BigDecimal.valueOf(Long.parseLong(String.valueOf(Math.round(Float.parseFloat(object[3].toString()))))));
            revenueResponse.setRealRevenue(BigDecimal.valueOf(Long.parseLong(String.valueOf(Math.round(Float.parseFloat(object[4].toString()))))));
            revenueResponse.setShippingAmount(BigDecimal.valueOf(Long.parseLong(String.valueOf(Math.round(Float.parseFloat(object[5].toString()))))));
            revenueResponse.setNetRevenue(BigDecimal.valueOf(Long.parseLong(String.valueOf(Math.round(Float.parseFloat(object[6].toString()))))));
            responses.add(revenueResponse);
        }
        Object counts = queryNativeCount.getSingleResult();
        int total = counts != null ? Integer.parseInt(counts.toString()) : 0;
        return new ListRevenueResponse(responses, new Metadata(total, filter.getPage(),
                filter.getLimit()));
    }

    public ListRevenueResponse getListRevenueRespone(RevenueFilterRequest filterRequest) {
        ListRevenueResponse listRevenueResponse = filterRevenue(filterRequest);
        String queryCount = buildNativeQuerySumTotal(filterRequest);
        Query queryNativeSumTotal = em.createNativeQuery(queryCount);
        String paramDay = "'%Y/%m/%d'";
        queryNativeSumTotal.setParameter("timeType", paramDay);
        if (filterRequest.getStartTime() != null) {
            queryNativeSumTotal.setParameter("timeCreated", filterRequest.getStartTime().atStartOfDay(ZoneId.systemDefault()).toInstant());
        }
        if (filterRequest.getEndTime() != null) {
            queryNativeSumTotal.setParameter("timeCreatedTo", filterRequest.getEndTime().atStartOfDay(ZoneId.systemDefault()).plusDays(1).minusSeconds(1).toInstant());
        }
        queryNativeSumTotal.setParameter("timeType", paramDay);

        List<Object[]> objects = queryNativeSumTotal.getResultList();
        for (Object[] object : objects) {
            listRevenueResponse.setTotalOrderAmount(BigDecimal.valueOf(Long.parseLong(String.valueOf(Math.round(Float.parseFloat(object[0].toString()))))));
            listRevenueResponse.setTotalOrderCount(Integer.parseInt(object[1].toString()));
            listRevenueResponse.setTotalReturnOrderAmount(BigDecimal.valueOf(Long.parseLong(String.valueOf(Math.round(Float.parseFloat(object[2].toString()))))));
            listRevenueResponse.setTotalRealRevenue(BigDecimal.valueOf(Long.parseLong(String.valueOf(Math.round(Float.parseFloat(object[3].toString()))))));
            listRevenueResponse.setTotalShippingFee(BigDecimal.valueOf(Long.parseLong(String.valueOf(Math.round(Float.parseFloat(object[4].toString()))))));
            listRevenueResponse.setTotalNetRevenue(BigDecimal.valueOf(Long.parseLong(String.valueOf(Math.round(Float.parseFloat(object[5].toString()))))));
        }
        return listRevenueResponse;
    }


    private StringBuilder buildQueryWithFilter(RevenueFilterRequest filter) {
        StringBuilder query = new StringBuilder("select DATE_FORMAT(orders.created_time, :timeType)        as date,\n" +
                "       ifnull(round(sum(total_price)), 0)               as totalAmount,\n" +
                "       ifnull(count(  orders.id), 0)                 as orderCount,\n" +
                "       ifnull(round(sum(orders.money_return)), 0)        as returnAmount,\n" +
                "       round(sum(total_price) - ifnull(round(sum(orders.money_return)), 0)) as realRevenue,\n" +
                "       ifnull(round(sum(fee_money)), 0)                        as shippingFee,\n" +
                "       round(sum(total_price) - ifnull(sum(orders.money_return), 0)) +\n" +
                "       ifnull(round(sum(fee_money)), 0)                        as netRevenue");
        buildCommonQuery(filter, query);
        query.append(" group by date");
        if (StringUtils.isEmpty(filter.getSortBy())) {
            query.append(" order by date desc");
        } else {
            switch (filter.getSortBy()) {
                case "totalAmount":
                    query.append(" order by totalAmount");
                    break;
                case "orderCount":
                    query.append(" order by orderCount");
                    break;
                case "returnAmount":
                    query.append(" order by returnAmount");
                    break;
                case "realRevenue":
                    query.append(" order by realRevenue");
                    break;
                case "shippingFee":
                    query.append(" order by shippingFee");
                    break;
                case "netRevenue":
                    query.append(" order by netRevenue");
                    break;
            }
            if (StringUtils.equals("desc", filter.getSortDirection())) {
                query.append(" desc");
            }
        }
        return query;
    }

    private String buildNativeQueryCount(RevenueFilterRequest filter) {
        StringBuilder query = new StringBuilder("select count(*) from ( ");
        query.append(buildQueryWithFilter(filter));
        query.append(" ) as count");
        return query.toString();
    }

    private String buildNativeQuerySumTotal(RevenueFilterRequest filter) {
        StringBuilder query = new StringBuilder("select sum(totalAmount) as totalOrderAmount,\n" +
                "       sum(orderCount) as totalOrderCount,\n" +
                "       sum(returnAmount) as totalReturnAmount,\n" +
                "       sum(realRevenue) as toalRealRevenue,\n" +
                "       sum(shippingFee) as totalShippingFee,\n" +
                "       sum(netRevenue) as totalNetRevenue\n" +
                "from ( ");
        query.append(buildQueryWithFilter(filter));
        query.append(" ) as tmp");
        return query.toString();
    }

    private void buildCommonQuery(RevenueFilterRequest filter, StringBuilder query) {
        query.append(" from order_purchase orders\n" +
                "         inner join order_by_status_history orderHis on orders.id = orderHis.order_purchase_id");
        query.append(" where orderHis.status_id in (8, 16)");
        if (filter.getStartTime() != null) {
            query.append(" and orders.created_time >= :timeCreated ");
        }
        if (filter.getEndTime() != null) {
            query.append(" and  orders.created_time <= :timeCreatedTo ");
        }
    }

    private String buildNativeQueryFilter(RevenueFilterRequest filter) {
        StringBuilder query = buildQueryWithFilter(filter);
        query.append(" limit :limit   ");
        query.append("     offset :offset");
        return query.toString();
    }
}
