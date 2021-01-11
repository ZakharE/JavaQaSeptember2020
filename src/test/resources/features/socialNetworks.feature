Feature: Social Networks

  Scenario Outline: Check social networks redirections
    Given I am on the main page
    When Click "<network>" icon
    Then New tab with "<link>" is opened


    Examples:
      | network | link                                                     |
      | vk      | https://vk.com/public145052891                             |
      | fb      | https://www.facebook.com/otusru/                        |
      | zen     | https://zen.yandex.ru/id/5bbcbc1ba5bd5400a990e7d9        |
      | ok      | https://ok.ru/group/54448251797611                       |
      | yt      | https://www.youtube.com/channel/UCetgtvy93o3i3CvyGXKFU3g |