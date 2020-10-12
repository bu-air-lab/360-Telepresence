<?php
    header('Content-Type: application/json');
    $aResult = array();

    if(isset($_GET['action_string']))
    {
      $current_act_input = $_GET['action_string'];
      $current_act_input = "The input sent was ".$current_act_input;
      $aResult['result'] = [$current_act_input];
      echo json_encode($aResult);
    }

    if(isset($_POST['action_string']))
    {
      $current_act_input = $_POST['action_string'];
      $pieces = explode("\n", $current_act_input);
      $current_act_input = "The input sent was ".$current_act_input;
      $aResult['result'] = [$pieces];
      echo json_encode($aResult);

      $myfile = fopen("./current_action.txt", "w") or die("Unable to open file!");
      for ($i=0; $i< count($pieces); $i++)
      {
        fwrite($myfile, $pieces[$i]."\n");
      }
      fclose($myfile);
    }


?>
