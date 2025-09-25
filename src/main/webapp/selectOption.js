var radioactiveParams = document.querySelectorAll(
  "select[name='selectedOption']"
);
var i;
for (i = 0; i < radioactiveParams.length; i++) {
  radioactiveParams[i].setAttribute("onchange", "onSelectOption(this)");
}

document.addEventListener("DOMContentLoaded", function () {
  document.querySelector("select[name='selectedOption']").onchange();
});

function onSelectOption(self) {
  var value = self.value;
  var selfId = self.parentNode.querySelector("input[name='name']").value;

  // Get enabled parameters list
  var enabledParameters = document
    .querySelector("[title='enabledParameters:" + value + ":" + selfId + "']")
    .getAttribute("value")
    .split(",");
  var enabledParametersSet = new Set(enabledParameters);
  var hideMode = document
    .querySelector("input[name='hideMode:" + selfId + "']")
    .getAttribute("value");

  console.log(
    "Option selected: " + value + ", enabled parameters: " + enabledParameters
  );

  // Loop through all existing parameters
  var allParameters = document.querySelectorAll(".jenkins-form-label");
  for (i = 0; i < allParameters.length; i++) {
    var parameter = allParameters[i];
    var formItem = parameter.closest(".jenkins-form-item");
    if (!formItem) continue;

    var parameterDiv = formItem.querySelector("div[name='parameter']");
    if (!parameterDiv) continue;

    var elem = parameterDiv.querySelector("input[name='name']");
    var isRadioactiveParam =
      parameterDiv.querySelector(
        "script[src='/plugin/radioactive-parameter-plugin/selectOption.js']"
      ) !== null;
    var id = elem !== null ? elem.value : "";

    if (selfId !== id) {
      if (enabledParametersSet.has(parameter.textContent.trim())) {
        // Show elements
        if (hideMode === "Hide elements") {
          formItem.style.display = "";
          if (isRadioactiveParam) {
            var childElem = parameterDiv.querySelector(
              "select[name='selectedOption']"
            );

            if (self.getAttribute("radioactiveParamParent") != id) {
              childElem.setAttribute("radioactiveParamParent", selfId);
              childElem.onchange();
            }
          }
        } else {
          var parameterElement = parameterDiv.lastChild;
          if (parameterElement) {
            parameterElement.style.backgroundColor = "white";
            parameterElement.style.userSelect = "auto";
            parameterElement.readOnly = false;
          }
        }
      } else {
        // Hide or disable elements
        if (hideMode === "Hide elements") {
          if (
            !isRadioactiveParam ||
            self.getAttribute("radioactiveParamParent") != id
          ) {
            formItem.style.display = "none";
          }
        } else {
          var parameterElement = parameterDiv.lastChild;
          if (parameterElement) {
            parameterElement.style.backgroundColor = "#DDD";
            parameterElement.style.userSelect = "none";
            parameterElement.readOnly = true;
          }
        }
      }
    }
  }
}