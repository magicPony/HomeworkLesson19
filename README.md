php request code : 




<?php
    // API access key from Google API's Console
    define( 'API_ACCESS_KEY', 'AIzaSyCUBNMWrbpRKUF7maN3mxOo1bDdQh9BCzc' );


    $registrationIds = array("APA91bELtjRrisCxcb70UYMIk_ue3-mu64LCq3u8X8gsn-E8xEJTmry2fWclUbiWTHDDOcD66nOG07PkKjy-v9uiPPaDKlTGZBFRDbJLXKwXojGQC5GwtqafZgvOL_OWrGuZqU7ls9cAfkdo9Hyfh423zPVrxz9sOw" );

    // prep the bundle
    $msg = array
    (
    'message'       => 'Еврокомиссия намерена включить в свое предложение по отмене визового режима некоторые гарантии на случай резкого всплеска миграции из Украины в ЕС',
    'title'         => 'Новость дня',
    'imageSrc'		=> 'http://www.fella-werke.de/media/images/News_765x350px.jpg',
    'sound'     	=> 1
    );

    $fields = array
    (
    'registration_ids'  => $registrationIds,
    'data'              => $msg
    );

    $headers = array
    (
    'Authorization: key=' . API_ACCESS_KEY,
    'Content-Type: application/json'
    );

    $ch = curl_init();
    curl_setopt( $ch,CURLOPT_URL, 'https://android.googleapis.com/gcm/send' );
    curl_setopt( $ch,CURLOPT_POST, true );
    curl_setopt( $ch,CURLOPT_HTTPHEADER, $headers );
    curl_setopt( $ch,CURLOPT_RETURNTRANSFER, true );
    curl_setopt( $ch,CURLOPT_SSL_VERIFYPEER, false );
    curl_setopt( $ch,CURLOPT_POSTFIELDS, json_encode( $fields ) );
    $result = curl_exec($ch );
    curl_close( $ch );

    echo $result;
?>
