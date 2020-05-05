$(document).ready(function() {
    updateIntra();
})

setInterval(function() {
    updateIntra();
}, 10000);




function updateIntra() {
    var i = 0;
    $.ajax("/area/IntraNoAuth/module", {
        success: function(data) {
            $('.module-info').text('');
            $.each(data, function(index, element) {
                if (i < 5)
                    $('.module-info').append(element.title + '<br />');
                i++;
            });
        },
        error: function() {
        }
    });
    var j = 0;
    $.ajax("/area/IntraNoAuth/projects", {
        success: function(data) {
            $('.project-info').text('');
            $.each(data, function(index, element) {
                if (j < 5)
                    $('.project-info').append(element.title + '<br />');
                j++;
            });
        },
        error: function() {
        }
    });
    var k = 0;
    $.ajax("/area/IntraNoAuth/notification", {
        success: function(data) {
            $('.info').text('');
            $.each(data, function(index, element) {
                if (k < 5)
                    $('.info').append(element.Title + '<br /><br />');
                k++;
            });
        },
        error: function() {
        }
    });
}