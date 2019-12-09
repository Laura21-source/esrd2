    // Отображение размера плашек от значений показателей
    function statisticBlock (array) {
        var countMax = /*Math.max.apply(null, array);*/ array[0]['value'];
        //var sum75 = parseInt(countMax*0.75);
        var sum50 = countMax*0.5;
        var sum25 = countMax*0.25;
        console.log(countMax+' - '+sum50+' - '+sum25);
        var sumClass = 'w-25';
        for(var i in array) {
            var value = array[i]['value'];
            var pole = array[i]['pole'];
            if(value == countMax) {sumClass = 'w-100';}
            if(value < countMax && value > sum50) {sumClass = 'w-75';}
            if(value < sum50 && value > sum25) {sumClass = 'w-50';}
            if(value < sum25) {sumClass = 'w-25';}
            $(pole).addClass(sumClass).html(value);
        }
    }

    // Массив JSON со всеми значениями сумм элементов
    function countElemJSON (url, value) {
        $.getJSON(url, function(data) {
            if(value == 1) {
                var mySum = data.atThisMounthOnControl;
                var mySuccess = data.atThisMounthOnControlCompletedInTime;
                var myWarning = data.atThisMounthOnControlCompletedAfterTime;
                var myDanger = data.atThisMounthOnControlNotCompleted;
                var myDis = [{'pole':'#mySum','value':mySum},{'pole':'#mySuccess','value':mySuccess},{'pole':'#myWarning','value':myWarning},{'pole':'#myDanger','value':myDanger}];
                statisticBlock(myDis);
            }
            if(value == 2) {
                var inWorkSuccess = data.inworkMoreDeadlineByDepartment;
                var inWorkDanger = data.inworkLessDeadlineByDepartment;
                var allInWork = [{'pole':'#inWorkSuccess','value':inWorkSuccess},{'pole':'#inWorkDanger','value':inWorkDanger}];
                statisticBlock(allInWork);
                // Для бубликов
                var inWorkSuccessUser = data.inworkMoreDeadlineByUserName;
                var inWorkDangerUser = data.inworkLessDeadlineByUserName;
                am4core.ready(function () {
                    am4core.useTheme(am4themes_dark);
                    am4core.useTheme(am4themes_animated);
                    var chart = am4core.create("chartDiv1", am4charts.PieChart3D);
                    chart.hiddenState.properties.opacity = 0; // this creates initial fade-in

                    chart.data = [
                        {
                            country: "Срок контроля 3 дня и меньше",
                            litres: inWorkDangerUser,
                            color: am4core.color("#ff4444")
                        },
                        {
                            country: "Срок контроля более 3 дней",
                            litres: inWorkSuccessUser,
                            color: am4core.color("#2BBBAD")
                        }
                    ];
                    var pieSeries = chart.series.push(new am4charts.PieSeries3D());
                    pieSeries.dataFields.value = "litres";
                    pieSeries.dataFields.category = "country";
                    pieSeries.slices.template.propertyFields.fill = "color";
                    chart.innerRadius = am4core.percent(63);
                    pieSeries.labels.template.disabled = true;
                    pieSeries.ticks.template.disabled = true;
                });
            }
            if(value == 3) {
                var agreeSuccess = data.agreementMoreDeadlineByDepartment;
                var agreeDanger = data.agreementLessDeadlineByDepartment;
                var allAgrre = [{'pole':'#agreeSuccess','value':agreeSuccess},{'pole':'#agreeDanger','value':agreeDanger}];
                statisticBlock(allAgrre);
                // Для бубликов
                var agreeSuccessUser = data.agreementLessDeadlineByUserName;
                var agreeDangerUser = data.agreementMoreDeadlineByUserName;
                am4core.ready(function() {
                    am4core.useTheme(am4themes_dark);
                    am4core.useTheme(am4themes_animated);
                    var chart = am4core.create("chartDiv2", am4charts.PieChart3D);
                    chart.hiddenState.properties.opacity = 0; // this creates initial fade-in
                    chart.data = [
                        {
                            country: "Срок контроля 3 дня и меньше",
                            litres: agreeDangerUser,
                            color: am4core.color("#ff4444")
                        },
                        {
                            country: "Срок контроля более 3 дней",
                            litres: agreeSuccessUser,
                            color: am4core.color("#2BBBAD")
                        }
                    ];
                    var pieSeries = chart.series.push(new am4charts.PieSeries3D());
                    pieSeries.dataFields.value = "litres";
                    pieSeries.dataFields.category = "country";
                    pieSeries.slices.template.propertyFields.fill = "color";
                    chart.innerRadius = am4core.percent(63);
                    pieSeries.labels.template.disabled = true;
                    pieSeries.ticks.template.disabled = true;
                });
            }
            if(value == 4) {
                var distSuccess = data.distributionMoreDeadlineByDepartment;
                var distDanger = data.distributionLessDeadlineByDepartment;
                var allDist = [{'pole':'#distSuccess','value':distSuccess},{'pole':'#distDanger','value':distDanger}];
                statisticBlock(allDist);
                var distSuccessUser = data.distributionMoreDeadlineByChiefUserName;
                var distDangerUser = data.distributionLessDeadlineByChiefUserName;
                am4core.ready(function() {
                    am4core.useTheme(am4themes_dark);
                    am4core.useTheme(am4themes_animated);
                    var chart = am4core.create("chartDiv3", am4charts.PieChart3D);
                    chart.hiddenState.properties.opacity = 0; // this creates initial fade-in
                    chart.data = [
                        {
                            country: "Срок контроля 3 дня и меньше",
                            litres: distDangerUser,
                            color: am4core.color("#ff4444")
                        },
                        {
                            country: "Срок контроля более 3 дней",
                            litres: distSuccessUser,
                            color: am4core.color("#2BBBAD")
                        }
                    ];
                    var pieSeries = chart.series.push(new am4charts.PieSeries3D());
                    pieSeries.dataFields.value = "litres";
                    pieSeries.dataFields.category = "country";
                    pieSeries.slices.template.propertyFields.fill = "color";
                    chart.innerRadius = am4core.percent(63);
                    pieSeries.labels.template.disabled = true;
                    pieSeries.ticks.template.disabled = true;
                });
            }
        })
    }