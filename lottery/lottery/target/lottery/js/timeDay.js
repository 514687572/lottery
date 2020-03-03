function datetimeDay(longTypeDate){  
    var dateTypeDate = "";  
    var date = new Date(); 
    date.setTime(longTypeDate);
    dateTypeDate += " " + getFullYear(date); //年
    dateTypeDate += "/" + getMonth(date); //月
    dateTypeDate += "/" + getDay(date); //日
    dateTypeDate += " " + getHours(date); //时  
    dateTypeDate += ":" + getMinutes(date);  //分 
    dateTypeDate += ":" + getSeconds(date);  //秒
    dateTypeDate += ":" + getMilliseconds(date); //毫秒
    return dateTypeDate; 
   }  
  
   function getFullYear(date){  
    var year = "";  
    year = date.getFullYear()
    if(year<10){  
        year = "0" + month;  
    }  
    return year;  
   } 
   //返回 01-12 的月份值  
   function getMonth(date){  
    var month = "";  
    month = date.getMonth() + 1; //getMonth()得到的月份是0-11  
    if(month<10){  
     month = "0" + month;  
    }  
    return month;  
   }  
   //返回01-30的日期  
   function getDay(date){  
    var day = "";  
    day = date.getDate();  
    if(day<10){  
     day = "0" + day;  
    }  
    return day;  
   } 
   //小时 
   function getHours(date){ 
    var hours = ""; 
    hours = date.getHours(); 
    if(hours<10){  
     hours = "0" + hours;  
    }  
    return hours;  
   } 
   //分 
   function getMinutes(date){ 
    var minute = ""; 
    minute = date.getMinutes(); 
    if(minute<10){  
     minute = "0" + minute;  
    }  
    return minute;  
   } 
   //秒 
   function getSeconds(date){ 
    var second = ""; 
    second = date.getSeconds(); 
    if(second<10){  
     second = "0" + second;  
    }  
    return second;  
   }
   function getMilliseconds(date){
       var Milliseconds="";
       Milliseconds=date.getMilliseconds()/100;
       return Milliseconds;
   }

// function timestampToTime(timestamp) {
//     var date = new Date(timestamp);//时间戳为10位需*1000，时间戳为13位的话不需乘1000
//     h = date.getHours() + ':';
//     m = (date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes()) +':'; 
//     s = date.getSeconds() +':';
//     ms = date.getMilliseconds();//毫秒值
    
//     return M + D + h + m;//此处可以自定义需要的显示类型
// }
