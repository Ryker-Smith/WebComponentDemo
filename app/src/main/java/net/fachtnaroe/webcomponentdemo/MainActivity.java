package net.fachtnaroe.webcomponentdemo;

import com.google.appinventor.components.runtime.Button;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.Form;
import com.google.appinventor.components.runtime.HandlesEventDispatching;
import com.google.appinventor.components.runtime.HorizontalArrangement;
import com.google.appinventor.components.runtime.Label;
import com.google.appinventor.components.runtime.TextBox;
import com.google.appinventor.components.runtime.VerticalArrangement;
import com.google.appinventor.components.runtime.Web;

import org.json.JSONObject;
import org.w3c.dom.Text;

public class MainActivity extends Form implements HandlesEventDispatching {

    private
    HorizontalArrangement topLayout, midLayout;
    VerticalArrangement mainLayout, contentLayout;
    TextBox servernameBox, commandBox, contentBox;
    Label servernameBoxLabel, commandBoxLabel;
    Button goButton;
    Web contentGetter;

    protected void $define() {

        this.Sizing("Responsive");
        mainLayout = new VerticalArrangement(this);
        mainLayout.AlignHorizontal(Component.ALIGNMENT_CENTER);
        mainLayout.WidthPercent(100);
        mainLayout.HeightPercent(100);
        topLayout =new HorizontalArrangement(mainLayout);
        topLayout.WidthPercent(100);
        topLayout.HeightPercent(5);
        topLayout.AlignHorizontal(Component.ALIGNMENT_CENTER);
        topLayout.BackgroundColor(Component.COLOR_LTGRAY);
        topLayout.AlignVertical(Component.ALIGNMENT_CENTER);
        servernameBoxLabel=new Label(topLayout);
        servernameBoxLabel.Text("Server:");
        servernameBoxLabel.BackgroundColor(Component.COLOR_LTGRAY);
        servernameBoxLabel.FontSize(12);
        servernameBoxLabel.FontBold(true);
        servernameBoxLabel.WidthPercent(20);
        servernameBoxLabel.HeightPercent(100);
        servernameBox =new TextBox(topLayout);
        servernameBox.FontSize(12);
        servernameBox.WidthPercent(75);
        servernameBox.Text("https://fachtnaroe.net/qndco2?");
        servernameBox.Enabled(false);
        servernameBox.FontTypeface(Component.TYPEFACE_MONOSPACE);

        midLayout = new HorizontalArrangement(mainLayout);
        midLayout.AlignHorizontal(Component.ALIGNMENT_CENTER);
        midLayout.BackgroundColor(Component.COLOR_LTGRAY);
        midLayout.WidthPercent(100);

        commandBoxLabel=new Label(midLayout);
        commandBoxLabel.Text("API Command:");
        commandBoxLabel.FontBold(true);
        commandBoxLabel.FontSize(12);
        commandBoxLabel.WidthPercent(20);
        commandBox=new TextBox(midLayout);
        commandBox.FontSize(12);
        commandBox.WidthPercent(75);
        commandBox.FontTypeface(Component.TYPEFACE_MONOSPACE);
        commandBox.Text("cmd=debug");

        contentLayout=new VerticalArrangement(mainLayout);
        goButton = new Button(contentLayout);
        goButton.Text("go");
        goButton.WidthPercent(100);
        contentBox=new TextBox(contentLayout);
        contentBox.MultiLine(true);
        contentBox.WidthPercent(100);
        contentBox.HeightPercent(100);
        contentBox.Text("Reply will be here");
        contentBox.BackgroundColor(Component.COLOR_LTGRAY);

        contentGetter = new Web (mainLayout);

        EventDispatcher.registerEventForDelegation(this, formName, "Click");
        EventDispatcher.registerEventForDelegation(this, formName, "GotText");
        EventDispatcher.registerEventForDelegation(this, formName, "BackPressed");
        EventDispatcher.registerEventForDelegation(this, formName, "OtherScreenClosed");
    }

    public boolean dispatchEvent(Component component, String componentName, String eventName, Object[] params) {

        System.err.print("dispatchEvent: " + formName + " [" + component.toString() + "] [" + componentName + "] " + eventName);
        if (eventName.equals("BackPressed")) {
            // this would be a great place to do something useful
            return true;
        }
        else if (eventName.equals("Click")) {
            if (component.equals(goButton)) {
                contentGetter.Url( servernameBox.Text() + commandBox.Text() );
                contentBox.Text(contentGetter.Url());
                goButton.Text("working");
                System.err.print("You pressed the button");
                contentGetter.Get();
                return true;
            }
        }
        else if (eventName.equals("GotText")) {
            if (component.equals(contentGetter)) {
                String status = params[1].toString();
                String textOfResponse = (String) params[3];
                handleWebResponse(status, textOfResponse);
                return true;
            }
        }
        return false;
    }

    public void handleWebResponse(String status, String textOfResponse) {
        String temp = new String();
        if (status.equals("200"))  {
            contentBox.Text(textOfResponse);
        }
        else {
            contentBox.Text(status);
        }
    }
}

//    JSONObject parser = new JSONObject(textOfResponse);
//            if (parser.getInt("link_ID") >= 1) {
//                    messagesPopUp.ShowMessageDialog("Chat has been initiated; now wait for the response", "Information", "OK");
//                    } else {
//                    messagesPopUp.ShowMessageDialog("Could not initiate chat", "Information", "OK");
//                    }
//                    } catch (JSONException e) {
//                    // if an exception occurs, code for it in here
//                    messagesPopUp.ShowMessageDialog("Chat already initiated" + temp, "Information", "OK");
//                    }
//                    else {
//                    messagesPopUp.ShowMessageDialog("Problem connecting with server", "Information", "OK");
//                    }