(() => {
    let clientWidth = $(window).width();
    let htmlSize = parseInt(clientWidth / 375 * 100) + 'px';
    $('html').css({'font-size': htmlSize});
})();