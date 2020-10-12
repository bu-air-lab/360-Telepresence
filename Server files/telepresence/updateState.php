<?php
    header('Content-Type: application/json');


    $aResult = array();




    if( isset($_GET["frame_number"]) )
    {
      getFrameState(intval($_GET["frame_number"]));
    }

    function getFrameState($input_frame_number)
    {
      $myfile = fopen("detection_frames.txt", "r") or die("Unable to open file!");
      $i =0;
      // echo $input_frame_number;
      // Output one character until end-of-file
      // while(!feof($myfile)) {
      //   if(fgetc($myfile) === "\n")
      //   {
      //     echo $i;
      //     $i++;
      //   }
      //   // echo fgetc($myfile);
      // }
      // fclose($myfile);
      $current_object_properties = [];
      $file = file_get_contents('./detection_frames.txt', true);
      $pieces = explode("\n", $file);
      for($i=0; $i<count($pieces)-3; $i+=3)
      {
        $current_frame_number_tag = $pieces[$i];
        $linearray = explode('_', $current_frame_number_tag);
        $current_frame_number_tag = $linearray[0];
        if(intval($current_frame_number_tag) === $input_frame_number)
        {
          // print_r($i);
          $current_frame_objects = $pieces[$i+1];
          $current_frame_objects = str_replace("[","",$current_frame_objects);
          $current_frame_objects = str_replace("]","",$current_frame_objects);
          $current_frame_individual_objects = explode('),', $current_frame_objects);
          // print_r($current_frame_number_tag."\n");
          // print_r($current_frame_objects."\n");
          for ($j=0; $j<count($current_frame_individual_objects); $j++)
          {
            $current_frame_individual_objects[$j] = str_replace("(","",$current_frame_individual_objects[$j]);
            $current_frame_individual_objects[$j] = str_replace(")","",$current_frame_individual_objects[$j]);
            $current_object_properties[$j] = explode(',', $current_frame_individual_objects[$j]);
            // print_r(($current_object_properties));

          }
          // print_r(($current_frame_individual_objects));

        }
      }
      $wedge_state = [0,0,0,0,0,0,0,0];
      $goal_object_wedge = -1;
      for($k=0; $k<count($current_object_properties); $k++)
      {
        // print_r($current_object_properties[$k]);
        $current_object_data = $current_object_properties[$k];
        $current_object_data[0] = str_replace("'","",$current_object_data[0]);
        $current_object_data[0] = str_replace(" ","",$current_object_data[0]);


        $image_width = 3840;
        $no_of_wedges = 8;
        $current_object_wedge = intval(intval($current_object_data[2])/($image_width/$no_of_wedges));
        if($current_object_data[0] === "tvmonitor")
        {
          $goal_object_wedge = $current_object_wedge;
          // echo "Object found."."\n".$k;
        }
        else
        {

          $wedge_state[$current_object_wedge] = 1;
          // print_r(intval(intval($current_object_data[2])/($image_width/$no_of_wedges)));  // Current wedge number of the object
          // print_r("\n");
          // print_r("\n");
        }
        // print_r($current_object_data);
      }

      // print_r($wedge_state);
      if($goal_object_wedge >= 0)
      {
        if($wedge_state[$goal_object_wedge] === 0)
        {
          $wedge_state[$goal_object_wedge] = 2;
        }
        else if($wedge_state[$goal_object_wedge] === 1)
        {
          $wedge_state[$goal_object_wedge] = 3;

        }
      }
      // print_r($wedge_state);
      // print_r("\n");

      return $wedge_state;

      // echo $file;
    }

    $result = "";
    if( !isset($_POST['functionname']) ) { $aResult['error'] = 'No function name!'; }

    if( !isset($_POST['arguments']) ) { $aResult['error'] = 'No function arguments!'; }

    if( !isset($aResult['error']) ) {

        switch($_POST['functionname']) {
            case 'updateCurrentState':
                $frame_number = $_POST['arguments'][0];
                $result = getFrameState(intval($frame_number));
                // $aResult['result'] = 0;
                $aResult['result'] = [$result];
                $myfile = fopen("./current_state.txt", "w") or die("Unable to open file!");
                $comma_separated_state = implode(",", $result);
                fwrite($myfile, $comma_separated_state."\n");
                fclose($myfile);



                echo json_encode($aResult);

                break;

            case 'removeTeamMember':
                break;

            default:
               $aResult['error'] = 'Not found function '.$_POST['functionname'].'!';
               break;

        }

    }


?>
