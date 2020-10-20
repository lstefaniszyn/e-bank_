/*
   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to You under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
var showControllersOnly = false;
var seriesFilter = "";
var filtersOnlySampleSeries = true;

/*
 * Add header in statistics table to group metrics by category
 * format
 *
 */
function summaryTableHeader(header) {
    var newRow = header.insertRow(-1);
    newRow.className = "tablesorter-no-sort";
    var cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 1;
    cell.innerHTML = "Requests";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 3;
    cell.innerHTML = "Executions";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 7;
    cell.innerHTML = "Response Times (ms)";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 1;
    cell.innerHTML = "Throughput";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 2;
    cell.innerHTML = "Network (KB/sec)";
    newRow.appendChild(cell);
}

/*
 * Populates the table identified by id parameter with the specified data and
 * format
 *
 */
function createTable(table, info, formatter, defaultSorts, seriesIndex, headerCreator) {
    var tableRef = table[0];

    // Create header and populate it with data.titles array
    var header = tableRef.createTHead();

    // Call callback is available
    if(headerCreator) {
        headerCreator(header);
    }

    var newRow = header.insertRow(-1);
    for (var index = 0; index < info.titles.length; index++) {
        var cell = document.createElement('th');
        cell.innerHTML = info.titles[index];
        newRow.appendChild(cell);
    }

    var tBody;

    // Create overall body if defined
    if(info.overall){
        tBody = document.createElement('tbody');
        tBody.className = "tablesorter-no-sort";
        tableRef.appendChild(tBody);
        var newRow = tBody.insertRow(-1);
        var data = info.overall.data;
        for(var index=0;index < data.length; index++){
            var cell = newRow.insertCell(-1);
            cell.innerHTML = formatter ? formatter(index, data[index]): data[index];
        }
    }

    // Create regular body
    tBody = document.createElement('tbody');
    tableRef.appendChild(tBody);

    var regexp;
    if(seriesFilter) {
        regexp = new RegExp(seriesFilter, 'i');
    }
    // Populate body with data.items array
    for(var index=0; index < info.items.length; index++){
        var item = info.items[index];
        if((!regexp || filtersOnlySampleSeries && !info.supportsControllersDiscrimination || regexp.test(item.data[seriesIndex]))
                &&
                (!showControllersOnly || !info.supportsControllersDiscrimination || item.isController)){
            if(item.data.length > 0) {
                var newRow = tBody.insertRow(-1);
                for(var col=0; col < item.data.length; col++){
                    var cell = newRow.insertCell(-1);
                    cell.innerHTML = formatter ? formatter(col, item.data[col]) : item.data[col];
                }
            }
        }
    }

    // Add support of columns sort
    table.tablesorter({sortList : defaultSorts});
}

$(document).ready(function() {

    // Customize table sorter default options
    $.extend( $.tablesorter.defaults, {
        theme: 'blue',
        cssInfoBlock: "tablesorter-no-sort",
        widthFixed: true,
        widgets: ['zebra']
    });

    var data = {"OkPercent": 99.89082969432314, "KoPercent": 0.1091703056768559};
    var dataset = [
        {
            "label" : "KO",
            "data" : data.KoPercent,
            "color" : "#FF6347"
        },
        {
            "label" : "OK",
            "data" : data.OkPercent,
            "color" : "#9ACD32"
        }];
    $.plot($("#flot-requests-summary"), dataset, {
        series : {
            pie : {
                show : true,
                radius : 1,
                label : {
                    show : true,
                    radius : 3 / 4,
                    formatter : function(label, series) {
                        return '<div style="font-size:8pt;text-align:center;padding:2px;color:white;">'
                            + label
                            + '<br/>'
                            + Math.round10(series.percent, -2)
                            + '%</div>';
                    },
                    background : {
                        opacity : 0.5,
                        color : '#000'
                    }
                }
            }
        },
        legend : {
            show : true
        }
    });

    // Creates APDEX table
    createTable($("#apdexTable"), {"supportsControllersDiscrimination": true, "overall": {"data": [0.3817841547099189, 1500, 3000, "Total"], "isController": false}, "titles": ["Apdex", "T (Toleration threshold)", "F (Frustration threshold)", "Label"], "items": [{"data": [0.03139013452914798, 1500, 3000, "e_bank GetAccount Counter value - 03"], "isController": false}, {"data": [0.13990825688073394, 1500, 3000, "e_bank GetCustomer Counter value - 03"], "isController": false}, {"data": [1.0, 1500, 3000, "e_bank GetApiStatus Counter value - 01"], "isController": false}, {"data": [0.11847826086956521, 1500, 3000, "e_bank GetCustomer Counter value - 02"], "isController": false}, {"data": [0.054906542056074766, 1500, 3000, "e_bank GetAccount Counter value - 04"], "isController": false}, {"data": [0.11322645290581163, 1500, 3000, "e_bank GetCustomer Counter value - 01"], "isController": false}, {"data": [0.024079320113314446, 1500, 3000, "e_bank GetAccount Counter value - 05"], "isController": false}, {"data": [1.0, 1500, 3000, "e_bank GetApiStatus Counter value - 04"], "isController": false}, {"data": [1.0, 1500, 3000, "e_bank GetApiStatus Counter value - 05"], "isController": false}, {"data": [0.0398406374501992, 1500, 3000, "e_bank GetAccount Counter value - 01"], "isController": false}, {"data": [1.0, 1500, 3000, "e_bank GetApiStatus Counter value - 02"], "isController": false}, {"data": [0.029661016949152543, 1500, 3000, "e_bank GetAccount Counter value - 02"], "isController": false}, {"data": [1.0, 1500, 3000, "e_bank GetApiStatus Counter value - 03"], "isController": false}, {"data": [0.1265625, 1500, 3000, "e_bank GetCustomer Counter value - 05"], "isController": false}, {"data": [0.12692307692307692, 1500, 3000, "e_bank GetCustomer Counter value - 04"], "isController": false}]}, function(index, item){
        switch(index){
            case 0:
                item = item.toFixed(3);
                break;
            case 1:
            case 2:
                item = formatDuration(item);
                break;
        }
        return item;
    }, [[0, 0]], 3);

    // Create statistics table
    createTable($("#statisticsTable"), {"supportsControllersDiscrimination": true, "overall": {"data": ["Total", 6412, 7, 0.1091703056768559, 7716.016531503419, 61, 30469, 5875.5, 19926.4, 22059.699999999997, 24694.22, 15.255551907419832, 7097.730170752827, 2.026999283706395], "isController": false}, "titles": ["Label", "#Samples", "KO", "Error %", "Average", "Min", "Max", "Median", "90th pct", "95th pct", "99th pct", "Transactions\/s", "Received", "Sent"], "items": [{"data": ["e_bank GetAccount Counter value - 03", 446, 1, 0.2242152466367713, 12736.706278026899, 1405, 30395, 12900.0, 22693.6, 24144.45, 26554.73999999999, 1.0737983570403613, 1455.215157116743, 0.1468083691266119], "isController": false}, {"data": ["e_bank GetCustomer Counter value - 03", 436, 0, 0.0, 10335.839449541285, 66, 24049, 9896.0, 20202.700000000004, 21745.6, 23393.989999999998, 1.0547247313596757, 0.23072103498492905, 0.1452306514860491], "isController": false}, {"data": ["e_bank GetApiStatus Counter value - 01", 499, 0, 0.0, 106.16833667334666, 61, 780, 69.0, 210.0, 286.0, 610.0, 1.2388159005372341, 0.23711710596220495, 0.15364220641428586], "isController": false}, {"data": ["e_bank GetCustomer Counter value - 02", 460, 0, 0.0, 10310.156521739138, 67, 27488, 9676.5, 20122.600000000006, 21519.55, 23336.759999999987, 1.117557311254288, 0.2444656618368755, 0.15388240320981894], "isController": false}, {"data": ["e_bank GetAccount Counter value - 04", 428, 0, 0.0, 12193.009345794391, 1267, 30469, 12402.0, 21672.6, 23879.85, 25522.869999999988, 1.0349438639677524, 1405.1496259361045, 0.14149623140184114], "isController": false}, {"data": ["e_bank GetCustomer Counter value - 01", 499, 1, 0.20040080160320642, 9913.188376753504, 64, 30190, 9871.0, 19437.0, 21215.0, 22500.0, 1.2038484550209405, 0.2634643604163048, 0.16576428921675063], "isController": false}, {"data": ["e_bank GetAccount Counter value - 05", 353, 2, 0.56657223796034, 12779.373937677054, 1527, 30270, 12714.0, 21499.0, 23306.5, 26723.819999999985, 0.8651409469004426, 1169.371796600431, 0.11828098883404489], "isController": false}, {"data": ["e_bank GetApiStatus Counter value - 04", 391, 0, 0.0, 105.33503836317136, 62, 981, 69.0, 226.40000000000003, 278.19999999999993, 676.8799999999994, 0.9530750686774682, 0.18242452486404664, 0.11820364621292818], "isController": false}, {"data": ["e_bank GetApiStatus Counter value - 05", 320, 0, 0.0, 110.278125, 62, 627, 70.0, 219.90000000000003, 316.5499999999999, 587.8000000000004, 0.801741784025796, 0.15345838834868752, 0.09943477204226182], "isController": false}, {"data": ["e_bank GetAccount Counter value - 01", 502, 1, 0.199203187250996, 12284.681274900391, 1040, 30395, 11165.5, 22704.6, 23798.85, 25331.34, 1.2180770397330907, 1652.6091190855323, 0.1665339702760085], "isController": false}, {"data": ["e_bank GetApiStatus Counter value - 02", 460, 0, 0.0, 101.31521739130439, 61, 692, 70.0, 179.80000000000007, 283.74999999999994, 543.78, 1.16816199867439, 0.22359350755876997, 0.1448794666324683], "isController": false}, {"data": ["e_bank GetAccount Counter value - 02", 472, 2, 0.423728813559322, 12636.769067796607, 1200, 30368, 11752.5, 23169.1, 24614.3, 27243.579999999994, 1.147670132371106, 1552.8083839187586, 0.15690802591011216], "isController": false}, {"data": ["e_bank GetApiStatus Counter value - 03", 436, 0, 0.0, 108.8715596330275, 61, 668, 70.0, 213.20000000000005, 320.15, 624.1899999999999, 1.0564830744626716, 0.20221746347137076, 0.13102866255542903], "isController": false}, {"data": ["e_bank GetCustomer Counter value - 05", 320, 0, 0.0, 10343.556249999998, 75, 24019, 10174.0, 20035.9, 21639.85, 23048.620000000003, 0.7908303224116371, 0.17299413302754563, 0.10889362837894613], "isController": false}, {"data": ["e_bank GetCustomer Counter value - 04", 390, 0, 0.0, 10779.723076923072, 65, 23923, 10352.0, 20375.4, 22164.35, 23602.059999999998, 0.9563699140738415, 0.20920591870365285, 0.13168765418399578], "isController": false}]}, function(index, item){
        switch(index){
            // Errors pct
            case 3:
                item = item.toFixed(2) + '%';
                break;
            // Mean
            case 4:
            // Mean
            case 7:
            // Median
            case 8:
            // Percentile 1
            case 9:
            // Percentile 2
            case 10:
            // Percentile 3
            case 11:
            // Throughput
            case 12:
            // Kbytes/s
            case 13:
            // Sent Kbytes/s
                item = item.toFixed(2);
                break;
        }
        return item;
    }, [[0, 0]], 0, summaryTableHeader);

    // Create error table
    createTable($("#errorsTable"), {"supportsControllersDiscrimination": false, "titles": ["Type of error", "Number of errors", "% in errors", "% in all samples"], "items": [{"data": ["500", 7, 100.0, 0.1091703056768559], "isController": false}]}, function(index, item){
        switch(index){
            case 2:
            case 3:
                item = item.toFixed(2) + '%';
                break;
        }
        return item;
    }, [[1, 1]]);

        // Create top5 errors by sampler
    createTable($("#top5ErrorsBySamplerTable"), {"supportsControllersDiscrimination": false, "overall": {"data": ["Total", 6412, 7, "500", 7, null, null, null, null, null, null, null, null], "isController": false}, "titles": ["Sample", "#Samples", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors"], "items": [{"data": ["e_bank GetAccount Counter value - 03", 446, 1, "500", 1, null, null, null, null, null, null, null, null], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": ["e_bank GetCustomer Counter value - 01", 499, 1, "500", 1, null, null, null, null, null, null, null, null], "isController": false}, {"data": ["e_bank GetAccount Counter value - 05", 353, 2, "500", 2, null, null, null, null, null, null, null, null], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": ["e_bank GetAccount Counter value - 01", 502, 1, "500", 1, null, null, null, null, null, null, null, null], "isController": false}, {"data": [], "isController": false}, {"data": ["e_bank GetAccount Counter value - 02", 472, 2, "500", 2, null, null, null, null, null, null, null, null], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}]}, function(index, item){
        return item;
    }, [[0, 0]], 0);

});
